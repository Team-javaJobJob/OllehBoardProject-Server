package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.MemberJoinRequest;
import com.example.ollethboardproject.controller.request.MemberLoginRequest;
import com.example.ollethboardproject.domain.dto.MemberDTO;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.exception.BoardException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.MemberRepository;
import com.example.ollethboardproject.utils.JwtTokenUtil;
import com.example.ollethboardproject.utils.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
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
                    throw new BoardException(ErrorCode.DUPLICATED_USERNAME, String.format("%s is duplicated", memberJoinRequest.getUserName()));
                });
        //TODO: 비밀번호 제약조건 설정 여부
        //member 비밀번호 암호화 후 엔티티 생성
        Member member = Member.of(encodePassword(memberJoinRequest));
        //member 엔티티 저장
        Member savedMember = memberRepository.save(member);
        //entity -> DTO 로 변환후 return
        return MemberDTO.fromEntity(savedMember);
    }
    @Transactional(readOnly = true)
    public TokenInfo login(MemberLoginRequest memberLoginRequest) {
        //아이디 체크
        Member member = memberRepository.findByUserName(memberLoginRequest.getUserName())
                .orElseThrow(() -> new BoardException(ErrorCode.USER_NOT_FOUND, String.format("%s is not found", memberLoginRequest.getUserName())));

        //패스워드 확인
        if (!encoder.matches(memberLoginRequest.getPassword(), member.getPassword())) {
            throw new BoardException(ErrorCode.INVALID_TOKEN, String.format("password is invalid"));
        }

        // 토큰 발급 (엑세스 , 리프레시)
        //TODO: 각 토큰들에 대한 세부 설정
        String accessToken = JwtTokenUtil.createAccessToken(member.getUserName(), key, accessExpiredTimeMs);
        String refreshToken = JwtTokenUtil.createRefreshToken(member.getUserName(), key, refreshExpiredTimeMs);
        return TokenInfo.generateTokens(accessToken, refreshToken);
    }

    public MemberDTO loadMemberByName(String userName) {
        return memberRepository.findByUserName(userName).map(MemberDTO::fromEntity).orElseThrow(() ->
                new BoardException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));
    }

    private MemberJoinRequest encodePassword(MemberJoinRequest memberJoinRequest) {
        //비밀번호 암호화
        String encodePassword = encoder.encode(memberJoinRequest.getPassword());
        memberJoinRequest.encode(encodePassword);
        return memberJoinRequest;
    }
}
