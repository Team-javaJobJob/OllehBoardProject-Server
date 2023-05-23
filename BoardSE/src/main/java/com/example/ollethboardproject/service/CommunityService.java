package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.community.CommunityCreateRequest;
import com.example.ollethboardproject.controller.request.community.CommunityUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.domain.dto.LocalCommunityDTO;
import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Keyword;
import com.example.ollethboardproject.domain.entity.LocalCommunity;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.CommunityRepository;
import com.example.ollethboardproject.repository.KeywordRepository;
import com.example.ollethboardproject.repository.LocalCommunityRepository;
import com.example.ollethboardproject.repository.MemberRepository;
import com.example.ollethboardproject.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final LocalCommunityRepository localCommunityRepository;
    private final MemberRepository memberRepository;

    private final KeywordRepository keywordRepository;

    @Transactional(readOnly = true)
    public List<CommunityDTO> findAllCommunities() {
        //TODO: LIST -> pageable
        List<Community> communities = communityRepository.findAll();
        return communities.stream().map(this::mapToCommunityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommunityDTO> findCommunitiesByKeyword(String keyword) {
        log.info("keyword: {}", keyword);

        List<Keyword> keywords = keywordRepository.findByKeyword(keyword);
        List<CommunityDTO> communityDTOList = keywords.stream()
                .map(Keyword::getCommunity)
                .map(this::mapToCommunityDto)
                .collect(Collectors.toList());

        //조회된 커뮤니티가 없을 시 에러 발생
        if(communityDTOList.isEmpty()){
            throw new OllehException(ErrorCode.COMMUNITY_DOES_NOT_EXIST);
        }

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
        LocalCommunity localCommunity = LocalCommunity.of(community, member);
        localCommunityRepository.save(localCommunity);

        //request에서 keywords 추출
        String[] keywords = communityCreateRequest.getKeywords();
        //키워드 저장
        Arrays.stream(keywords).forEach(keyword -> {
            Keyword savedKeyword = Keyword.of(keyword, community);
            keywordRepository.save(savedKeyword);
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
    public LocalCommunityDTO joinCommunity(Long communityId, Authentication authentication) {
        //커뮤니티가 없다면 에러 반환
        Community community = getCommunityByIdOrException(communityId);
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        //멤버가 해당 커뮤니티에 이미 등록되어있다면 에러를 반환한다.
        //TODO: findByIdAndMember에 중복되는 쿼리가 발생하는지 체크하기
        localCommunityRepository.findByCommunityAndMember(community, member).map(LocalCommunityDTO::fromEntity).ifPresent(findCommunity -> {
            throw new OllehException(ErrorCode.ALREADY_REGISTER);
        });
        // 유저가 선택한 커뮤니티 가입
        return LocalCommunityDTO.fromEntity(localCommunityRepository.save(LocalCommunity.of(community, member)));
    }

    @Transactional(readOnly = true)
    public List<LocalCommunityDTO> selectCommunity(Long communityId, Authentication authentication, Pageable pageable) {
        //커뮤니티 멤버만 조회 커뮤니티 조회할 수 있다.
        LocalCommunity localCommunity = getLocalCommunity(communityId,
                ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get());
        //승인되지 않은 멤버는 조회되지 않는다.
        return localCommunityRepository.findByCommunity(localCommunity.getCommunity(), pageable)
                .stream()
                .map(LocalCommunityDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private LocalCommunity getLocalCommunity(Long communityId, Member member) {
        Community community = getCommunityByIdOrException(communityId);
        return findLocalCommunityByCommunityAndMember(community, member);
    }

    private LocalCommunity findLocalCommunityByCommunityAndMember(Community community, Member member) {
        return localCommunityRepository.findByCommunityAndMember(community, member).orElseThrow(() ->
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
}
