package com.example.ollethboardproject.Service;

import com.example.ollethboardproject.controller.request.community.CommunityCreateRequest;
import com.example.ollethboardproject.controller.request.community.CommunityUpdateRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.repository.CommunityRepository;
import com.example.ollethboardproject.service.CommunityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class CommunityServiceTest {
    @InjectMocks
    private CommunityServiceTest communityServiceTest;
    @Mock
    private CommunityService communityService;
    @Mock
    private CommunityRepository communityRepository;

    @Test
    void 커뮤니티_전체_조회() {
        // given
        Member member = new Member("userName", "password", "nickName", Gender.FEMALE);
        Community community = Community.of(new CommunityCreateRequest("region", "interest", "info", "communityName"), member);

        List<Community> communityList = new ArrayList<>();
        communityList.add(community);

        List<CommunityDTO> result = communityList.stream()
                .map(a -> new CommunityDTO(a.getId(), a.getRegion(), a.getInterest(), a.getInfo(), a.getCommunityName(), a.getMember()))
                .collect(Collectors.toList());

        // when
        when(communityRepository.findAll()).thenReturn(communityList);
        when(communityService.findAllCommunities()).thenReturn(result);

        // then
        assertThat(result.get(0).getCommunityName()).isEqualTo(communityList.get(0).getCommunityName());
        Assertions.assertDoesNotThrow(() -> communityService.findAllCommunities());
    }

    @Test
    void 커뮤니티_생성() {
        // given
        // 회원 정보 세팅
        Member createMember = new Member("userName", "password", "nickName", Gender.FEMALE);
        CommunityCreateRequest communityCreateRequest = new CommunityCreateRequest("region", "interest", "info", "communityName");
        Community community = Community.of(communityCreateRequest, createMember);
        CommunityDTO result = CommunityDTO.fromEntity(community);

        // when
        when(communityRepository.save(community))
                .thenReturn(community);
        when(communityService.createCommunity(communityCreateRequest, mock(Authentication.class)))
                .thenReturn(result);

        // then
        assertThat(result.getCommunityName()).isEqualTo(community.getCommunityName());
        Assertions.assertDoesNotThrow(() -> communityService.findAllCommunities());
    }

    @Test
    void 커뮤니티_수정() {
        // given
        // 회원 정보 세팅
        Member updateMember = new Member("userName", "password", "nickName", Gender.FEMALE);
        Community community = Community.of(new CommunityCreateRequest("region", "interest", "info", "communityName"), updateMember);
        // 수정할 회원 정보 세팅
        CommunityUpdateRequest communityUpdateRequest = new CommunityUpdateRequest("region1", "interest1", "info1", "communityName1");
        community.update(communityUpdateRequest, updateMember);
        CommunityDTO result = CommunityDTO.fromEntity(community);

        // when
        when(communityRepository.save(community))
                .thenReturn(community);
        when(communityService.updateCommunity(any(Long.class), any(CommunityUpdateRequest.class), any(Authentication.class)))
                .thenReturn(result);

        // then
        assertThat(result.getCommunityName()).isEqualTo(community.getCommunityName());
        Assertions.assertDoesNotThrow(() ->
                communityService.updateCommunity(any(Long.class), any(CommunityUpdateRequest.class), any(Authentication.class)));
    }

    @Test
    void 커뮤니티_삭제() {
        // given
        // 회원 정보 세팅
        Member updateMember = new Member("userName", "password", "nickName", Gender.FEMALE);
        Community community = Community.of(new CommunityCreateRequest("region", "interest", "info", "communityName"), updateMember);

        // when
        doNothing().when(communityRepository).delete(community);
        doNothing().when(communityService).deleteCommunity(any(Long.class), any(Authentication.class));

        // then
        Assertions.assertDoesNotThrow(() ->
                communityService.deleteCommunity(any(Long.class), any(Authentication.class)));
    }
}