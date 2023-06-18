
package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.member.MemberJoinRequest;
import com.example.ollethboardproject.controller.request.member.MemberLoginRequest;
import com.example.ollethboardproject.controller.request.member.MemberUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.domain.dto.MemberDTO;
import com.example.ollethboardproject.domain.dto.PostDTO;
import com.example.ollethboardproject.domain.entity.*;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.*;
import com.example.ollethboardproject.utils.JwtTokenUtil;
import com.example.ollethboardproject.utils.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder encoder;
    private final OllehRepository ollehRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final MemberCacheRepository memberCacheRepository;
    private final TokenCacheRepository tokenCacheRepository;

    @Value("${jwt.token.secret}")
    private String key;
    @Value("${jwt.access-expired}")
    private Long accessExpiredTimeMs;
    @Value("${jwt.refresh-expired}")
    private Long refreshExpiredTimeMs;

    public MemberDTO join(MemberJoinRequest memberJoinRequest) {
        // 회원가입 중복 체크
        memberRepository.findByUserName(memberJoinRequest.getUserName())
                .ifPresent(member -> {
                    throw new OllehException(ErrorCode.DUPLICATED_USERNAME, String.format("%s is duplicated", memberJoinRequest.getUserName()));
                });

        // 비밀번호 암호화
        memberJoinRequest.encode(encodePassword(memberJoinRequest.getPassword()));
        // 비밀번호 암호화 후 member 타입으로 객체 생성
        Member member = Member.of(memberJoinRequest);

        // member 엔티티 저장
        Member savedMember = memberRepository.save(member);
        log.info("saveMember : {}", savedMember);
        // entity -> DTO 로 변환후 return
        return MemberDTO.fromEntity(savedMember);
    }

    @Override
    public Member loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("memberCacheRepository.getMember(username) {}", memberCacheRepository.getMember(username));
        return memberCacheRepository.getMember(username).orElseGet(() ->
                memberRepository.findByUserName(username).orElseThrow(() ->
                        new OllehException(ErrorCode.USER_NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    public TokenInfo login(MemberLoginRequest memberLoginRequest) {

        Member member = loadUserByUsername(memberLoginRequest.getUserName());
        log.info("member in login before setRedisMember {}", member.getUsername());
        memberCacheRepository.setMember(member);


        //패스워드 확인
        if (!encoder.matches(memberLoginRequest.getPassword(), member.getPassword())) {
            throw new OllehException(ErrorCode.INVALID_TOKEN, String.format("password is invalid"));
        }

        String accessToken = JwtTokenUtil.createAccessToken(member.getUsername(), key, accessExpiredTimeMs);
        String refreshToken = JwtTokenUtil.createRefreshToken(member.getUsername(), key, refreshExpiredTimeMs);

        tokenCacheRepository.setToken(accessToken, refreshToken);

        return TokenInfo.generateTokens(accessToken);
    }

    private String encodePassword(String password) {
        // 비밀번호 암호화
        return encoder.encode(password);
    }

    public MemberDTO findMemberByPassword(String requestPw, Authentication authentication) {
        Member member = loadUserByUsername(authentication.getName());
        // 회원 정보 조회 전 비밀번호 일치 여부 확인
        passwordMatches(member, requestPw);
        // entity -> DTO 로 변환후 return
        return MemberDTO.fromEntity(member);
    }

    public MemberDTO updateMember(MemberUpdateRequest memberUpdateRequest, Authentication authentication) {
        // 캐스팅에 의한 에러가 나지 않도록 ClassUtil 메서드 사용
        Member member = loadUserByUsername(authentication.getName());
        // 수정할 회원 정보 중복 검증
        duplicationMatches(memberUpdateRequest.getUserName());
        // 회원 정보 수정 전 비밀번호 일치 여부 확인
        passwordMatches(member, memberUpdateRequest.getRequestPw());
        // 수정할 비밀번호 암호화
        memberUpdateRequest.encode(encodePassword(memberUpdateRequest.getPassword()));
        // 비밀번호 암호화 후 member 타입으로 객체 생성
        Member updatedMember = Member.toPw(memberUpdateRequest);
        // 회원 정보 수정 (Setter 를 사용하지 않기 위함)
        member.update(updatedMember);
        // 수정된 회원 정보 저장
        memberRepository.save(member);
        return MemberDTO.fromEntity(updatedMember);
    }

    public void deleteMember(String requestPw, Authentication authentication) {
        // 캐스팅에 의한 에러가 나지 않도록 ClassUtil 메서드 사용
        Member member = loadUserByUsername(authentication.getName());
        // 회원 정보 삭제 전 비밀번호 일치 여부 확인
        passwordMatches(member, requestPw);
        // 회원 정보 삭제
        memberRepository.deleteById(member.getId());
    }

    @Transactional(readOnly = true)
    public List<CommunityDTO> selectOllehLog(Authentication authentication) {
        //member 조회
        Member member = loadUserByUsername(authentication.getName());

        return ollehRepository.findByMember(member).orElseThrow(
                        () -> new OllehException(ErrorCode.USER_NOT_FOUND)).stream()    //멤버에 대한 올레 버튼이 존재하지 않는다면 에러 반환
                .map(Olleh::getCommunity)   //커뮤니티 조회
                .sorted(Comparator.comparing(Community::getCreatedBy))//날짜순으로 정렬
                .map(CommunityDTO::fromEntity)  //DTO로 변환
                .collect(Collectors.toList());  //List로 변환
    }

    @Transactional(readOnly = true)
    public List<PostDTO> selectPostLog(Authentication authentication) {
        Member member = loadUserByUsername(authentication.getName());

        List<Post> posts = postRepository.findByMember(member).orElseThrow(() -> new OllehException(ErrorCode.USER_NOT_FOUND));
        return posts.stream()//멤버가 작성한 게시물이 없다면 에러 반환
                .sorted(Comparator.comparing(Post::getCreatedAt))
                .map(PostDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommunityDTO> selectCommunityLog(Authentication authentication) {
        Member member = loadUserByUsername(authentication.getName());
        return communityMemberRepository.findByMember(member).orElseThrow(() -> new OllehException(ErrorCode.USER_NOT_FOUND)).stream()
                .map(CommunityMember::getCommunity)
                .sorted(Comparator.comparing(Community::getCreatedAt))
                .map(CommunityDTO::fromEntity)
                .collect(Collectors.toList());


    }

    // 비밀번호 일치 검증 메서드
    private void passwordMatches(Member member, String password) {
        //비교할 password 암호화
        String encodePassword = encoder.encode(password);
        if (encoder.matches(encodePassword, member.getPassword())) {
            throw new OllehException(ErrorCode.HAS_NOT_PERMISSION_TO_ACCESS);
        }
    }

    // 수정된 회원 정보 중복 검증 메서드
    private void duplicationMatches(String updaterName) {
        memberRepository.findByUserName(updaterName).ifPresent(name -> {
            throw new OllehException(ErrorCode.DUPLICATED_USERNAME);
        });

    }
}

