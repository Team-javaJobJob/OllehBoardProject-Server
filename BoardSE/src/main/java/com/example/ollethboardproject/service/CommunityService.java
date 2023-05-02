package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.CommunityCreateRequest;
import com.example.ollethboardproject.controller.request.CommunityUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.exception.BoardException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.CommunityRepository;
import com.example.ollethboardproject.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.ollethboardproject.exception.ErrorCode.COMMUNITY_ALREADY_EXISTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    public List<CommunityDTO> findAllCommunities() {
        //TODO: LIST -> pageable
        List<Community> communities = communityRepository.findAll();
        return communities.stream().map(this::mapToCommunityDto).collect(Collectors.toList());
    }

    public CommunityDTO createCommunity(CommunityCreateRequest communityCreateRequest, Authentication authentication) {
        log.info("communityCreateRequest : {}", communityCreateRequest);
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        log.info("createCommunity member :  {}", member);
        Community community = Community.of(communityCreateRequest, member);
        log.info("createCommunity community :  {}", community);

        //커뮤니티 이름 중복 체크 메서드
        if (communityRepository.findByCommunityName(communityCreateRequest.getCommunityName()).isPresent()) {
            throw new BoardException(ErrorCode.COMMUNITY_ALREADY_EXISTS);
        }

        communityRepository.save(community);
        return mapToCommunityDto(community);
    }

    public CommunityDTO updateCommunity(Long id, CommunityUpdateRequest communityUpdateRequest, Authentication authentication) {
        //커뮤니티가 존재하지 않는다면 예외 발생
        Community community = communityRepository.findById(id).orElseThrow(() -> new BoardException(ErrorCode.COMMUNITY_DOES_NOT_EXIST));
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

    public void deleteBoard(Long id, Authentication authentication) {
        Community community = communityRepository.findById(id).orElseThrow(() -> new BoardException(ErrorCode.COMMUNITY_DOES_NOT_EXIST));
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        //커뮤니티 생성자만 커뮤니티를 삭제할 수 있다.
        validateMatches(community, member);
        communityRepository.delete(community);
    }

    // 커뮤니티 관리 권한 검증 메서드
    private void validateMatches(Community community, Member member) {
        if (community.getMember().getId() != member.getId()) {
            throw new BoardException(ErrorCode.HAS_NOT_PERMISSION_TO_ACCESS);
        }
    }

    // entity -> dto 변환 메서드
    private CommunityDTO mapToCommunityDto(Community community) {
        return CommunityDTO.fromEntity(community);
    }
}
