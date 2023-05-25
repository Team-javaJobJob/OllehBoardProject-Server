package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.community.CommunityCreateRequest;
import com.example.ollethboardproject.controller.request.community.CommunityUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.domain.dto.CommunityMemberDTO;
import com.example.ollethboardproject.domain.entity.*;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.*;
import com.example.ollethboardproject.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final KeywordRepository keywordRepository;
    private final OllehRepository ollehRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<CommunityDTO> findAllCommunities() {
        //TODO: LIST -> pageable
        List<Community> communities = communityRepository.findAll();
        return communities.stream().map(this::mapToCommunityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommunityDTO> findCommunitiesByKeyword(String keyword) {
        log.info("keyword: {}", keyword);

        List<Keyword> keywords = keywordRepository.findByKeyword(keyword).orElseThrow(
                () -> new OllehException(ErrorCode.KEYWORD_DOES_NOT_EXIST));

        List<CommunityDTO> communityDTOList = keywords.stream()
                .map(Keyword::getCommunity)
                .map(this::mapToCommunityDto)
                .collect(Collectors.toList());

        return communityDTOList;
    }

    @Transactional
    public CommunityDTO createCommunity(CommunityCreateRequest communityCreateRequest, Authentication authentication) {
        //커뮤니티 이름 중복 체크
        if (communityRepository.findByCommunityName(communityCreateRequest.getCommunityName()).isPresent()) {
            throw new OllehException(ErrorCode.COMMUNITY_ALREADY_EXISTS);
        }
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        //커뮤니티 생성
        Community community = Community.of(communityCreateRequest, member);
        communityRepository.save(community);
        //로컬 커뮤니티 생성
        CommunityMember communityMember = CommunityMember.of(community, member);
        communityMemberRepository.save(communityMember);

        //request에서 keywords 추출
        String[] keywords = communityCreateRequest.getKeywords();
        //키워드 저장
        Arrays.stream(keywords).forEach(keyword -> {
            keywordRepository.save(Keyword.of(keyword, community));
        });

        return mapToCommunityDto(community);
    }

    @Transactional
    public CommunityDTO updateCommunity(Long id, CommunityUpdateRequest communityUpdateRequest, Authentication authentication) {
        //커뮤니티가 존재하지 않는다면 예외 발생
        Community community = getCommunityByIdOrException(id);
        //캐스팅에 의한 에러가 나지 않도록 ClassUtil 메서드 사용
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        //커뮤니티 생성자만 커뮤니티 정보를 수정할 수 있다.
        validateMatches(community, member);
        //게시물 수정 (Setter 를 사용하지 않기 위함)
        community.update(communityUpdateRequest, member);
        //게시물 저장
        communityRepository.save(community);
        return mapToCommunityDto(community);
    }

    @Transactional
    public void deleteCommunity(Long id, Authentication authentication) {
        Community community = getCommunityByIdOrException(id);
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        //커뮤니티 생성자만 커뮤니티를 삭제할 수 있다.
        validateMatches(community, member);
        communityRepository.delete(community);
    }

    @Transactional
    public CommunityMemberDTO joinCommunity(Long communityId, Authentication authentication) {
        //커뮤니티가 없다면 에러 반환
        Community community = getCommunityByIdOrException(communityId);
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        //멤버가 해당 커뮤니티에 이미 등록되어있다면 에러를 반환한다.
        //TODO: findByIdAndMember에 중복되는 쿼리가 발생하는지 체크하기
        communityMemberRepository.findByCommunityAndMember(community, member).map(CommunityMemberDTO::fromEntity).ifPresent(findCommunity -> {
            throw new OllehException(ErrorCode.ALREADY_REGISTER);
        });
        // 유저가 선택한 커뮤니티 가입
        return CommunityMemberDTO.fromEntity(communityMemberRepository.save(CommunityMember.of(community, member)));
    }

    @Transactional(readOnly = true)
    public List<CommunityMemberDTO> selectCommunity(Long communityId, Authentication authentication, Pageable pageable) {
        //커뮤니티 멤버만 조회 커뮤니티 조회할 수 있다.
        CommunityMember localCommunity = getCommunityMember(communityId,
                ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get());
        //승인되지 않은 멤버는 조회되지 않는다.
        return communityMemberRepository.findByCommunity(localCommunity.getCommunity(), pageable)
                .stream()
                .map(CommunityMemberDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private CommunityMember getCommunityMember(Long communityId, Member member) {
        Community community = getCommunityByIdOrException(communityId);
        return findCommunityMemberByCommunityAndMember(community, member);
    }

    private CommunityMember findCommunityMemberByCommunityAndMember(Community community, Member member) {
        return communityMemberRepository.findByCommunityAndMember(community, member).orElseThrow(() ->
                new OllehException(ErrorCode.USER_NOT_FOUND));
    }

    // entity -> dto 변환 메서드
    private CommunityDTO mapToCommunityDto(Community community) {
        return CommunityDTO.fromEntity(community);
    }

    private static boolean isNotCreator(Community community, Member member) {
        return member.getId() != community.getMember().getId();
    }

    private Community getCommunityByIdOrException(Long communityId) {
        return communityRepository.findById(communityId).orElseThrow(() -> new OllehException(ErrorCode.COMMUNITY_DOES_NOT_EXIST));
    }

    private void validateMatches(Community community, Member member) {
        if (community.getMember().getId() != member.getId()) {
            throw new OllehException(ErrorCode.HAS_NOT_PERMISSION_TO_ACCESS);
        }
    }

    //Olleh(좋아요)

    //userName 를 인자로 받아 member 를 조회하고 존재하지 않으면 OllehException 발생
    private Member getMemberByMemberName(String userName){
        return memberRepository.findByUserName(userName)
                .orElseThrow(()-> new OllehException(ErrorCode.USER_NOT_FOUND));
    }

    //communityId, member 를 인자로 받아 조회하고 communityId 가 존재하지 않으면 OllehException 발생
    private Community getCommunityId(Long communityId){
        return communityRepository.findById(communityId)
                .orElseThrow(() -> new OllehException(ErrorCode.COMMUNITY_DOES_NOT_EXIST));
    }

    @Transactional //하나의 트랜잭션으로 묶어서 하나라도 실패하면 모두 롤백
    public boolean addOlleh(String userName, Long communityId){
        Member member = getMemberByMemberName(userName); //findByCommunityAndMember 메서드 호출하여 member 에 해당하는 member 를 가져옴
        Community community = getCommunityId(communityId); //findByIdAndMember 메서드 호출하여 communityId 에 해당하는 post 를 가져옴

        if (removeOlleh(member, community))
            return false; //removeOlleh 호출-> member,community 인자로 받아서 Olleh 객체삭제 -> true 반환시 false 반환

        ollehRepository.save(Olleh.of(member, community));
        return true; //Olleh 추가에 성공한 경우 true 반환
    }

    public boolean removeOlleh(Member member, Community community) {
        if(ollehRepository.findByMemberAndCommunity(member, community).isPresent()){ //isPresent() 메소드로 Optional 객체에 값이 있는지 확인
            Olleh olleh = ollehRepository.findByMemberAndCommunity(member, community).get(); //값이 있다면 get()으로 가져옴
            ollehRepository.delete(olleh); //가져온 값을 <-ollehRepository.delete(olleh)메서드로 삭제
            return true; //그리고 true 반환 (삭제 성공시 true 반환)
        }
        return false; //삭제할 객체가 없으면 false 반환
    }

    public Integer ollehCount(Long communityId) {
        Community community = getCommunityId(communityId); //communityId 에 해당하는 community 객체를 가져옴
        return ollehRepository.countByCommunity(community); //community 객체와 연관된 Olleh 객체의 개수 반환
    }

    //최신순 정렬
    public List<Community> getLatestCommunity(){
        return communityRepository.findAllByOrderByCreatedAtDesc();
    }

    //추천순 (올레순) 정렬
    public List<Community> getTopOllehCommunity() {
        return communityRepository.findAllByOrderByOllehDesc();
    }
}
