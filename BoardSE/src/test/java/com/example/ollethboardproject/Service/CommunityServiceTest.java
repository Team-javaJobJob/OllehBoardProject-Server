//package com.example.ollethboardproject.Service;
//
//import com.example.ollethboardproject.controller.request.community.CommunityCreateRequest;
//import com.example.ollethboardproject.controller.request.community.CommunityUpdateRequest;
//import com.example.ollethboardproject.domain.Gender;
//import com.example.ollethboardproject.domain.dto.CommunityDTO;
//import com.example.ollethboardproject.domain.entity.Community;
//import com.example.ollethboardproject.domain.entity.Member;
//import com.example.ollethboardproject.domain.entity.Olleh;
//import com.example.ollethboardproject.domain.entity.Post;
//import com.example.ollethboardproject.repository.*;
//import com.example.ollethboardproject.service.CommunityService;
//import com.example.ollethboardproject.service.PostService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.Authentication;
//
//import javax.transaction.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@Transactional
//class CommunityServiceTest {
//
//    @Mock private CommunityRepository communityRepository;
//    @Mock private OllehRepository ollehRepository;
//    @Mock private MemberRepository memberRepository;
//    @Mock private CommunityMemberRepository communityMemberRepository;
//    @Mock private KeywordRepository keywordRepository;
//    private CommunityService communityService;
//
////    @Test
////    void 커뮤니티_전체_조회() {
////        // given
////        Member member = new Member("userName", "password", "nickName", Gender.FEMALE);
////        Community community = Community.of(new CommunityCreateRequest("region", "interest", "info", "communityName"), member);
////
////        List<Community> communityList = new ArrayList<>();
////        communityList.add(community);
////
////        List<CommunityDTO> result = communityList.stream()
////                .map(a -> new CommunityDTO(a.getId(), a.getRegion(), a.getInterest(), a.getInfo(), a.getCommunityName(), a.getMember()))
////                .collect(Collectors.toList());
////
////        // when
////        when(communityRepository.findAll()).thenReturn(communityList);
////        when(communityService.findAllCommunities()).thenReturn(result);
////
////        // then
////        assertThat(result.get(0).getCommunityName()).isEqualTo(communityList.get(0).getCommunityName());
////        Assertions.assertDoesNotThrow(() -> communityService.findAllCommunities());
////    }
////
////    @Test
////    void 커뮤니티_생성() {
////        // given
////        // 회원 정보 세팅
////        Member createMember = new Member("userName", "password", "nickName", Gender.FEMALE);
////        CommunityCreateRequest communityCreateRequest = new CommunityCreateRequest("region", "interest", "info", "communityName");
////        Community community = Community.of(communityCreateRequest, createMember);
////        CommunityDTO result = CommunityDTO.fromEntity(community);
////
////        // when
////        when(communityRepository.save(community))
////                .thenReturn(community);
////        when(communityService.createCommunity(communityCreateRequest, mock(Authentication.class)))
////                .thenReturn(result);
////
////        // then
////        assertThat(result.getCommunityName()).isEqualTo(community.getCommunityName());
////        Assertions.assertDoesNotThrow(() -> communityService.findAllCommunities());
////    }
////
////    @Test
////    void 커뮤니티_수정() {
////        // given
////        // 회원 정보 세팅
////        Member updateMember = new Member("userName", "password", "nickName", Gender.FEMALE);
////        Community community = Community.of(new CommunityCreateRequest("region", "interest", "info", "communityName"), updateMember);
////        // 수정할 회원 정보 세팅
////        CommunityUpdateRequest communityUpdateRequest = new CommunityUpdateRequest("region1", "interest1", "info1", "communityName1");
////        community.update(communityUpdateRequest, updateMember);
////        CommunityDTO result = CommunityDTO.fromEntity(community);
////
////        // when
////        when(communityRepository.save(community))
////                .thenReturn(community);
////        when(communityService.updateCommunity(any(Long.class), any(CommunityUpdateRequest.class), any(Authentication.class)))
////                .thenReturn(result);
////
////        // then
////        assertThat(result.getCommunityName()).isEqualTo(community.getCommunityName());
////        Assertions.assertDoesNotThrow(() ->
////                communityService.updateCommunity(any(Long.class), any(CommunityUpdateRequest.class), any(Authentication.class)));
////    }
////
////    @Test
////    void 커뮤니티_삭제() {
////        // given
////        // 회원 정보 세팅
////        Member updateMember = new Member("userName", "password", "nickName", Gender.FEMALE);
////        Community community = Community.of(new CommunityCreateRequest("region", "interest", "info", "communityName"), updateMember);
////
////        // when
////        doNothing().when(communityRepository).delete(community);
////        doNothing().when(communityService).deleteCommunity(any(Long.class), any(Authentication.class));
////
////        // then
////        Assertions.assertDoesNotThrow(() ->
////                communityService.deleteCommunity(any(Long.class), any(Authentication.class)));
////    }
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); //모의 객체 초기화
//        communityService = new CommunityService(communityRepository,communityMemberRepository,keywordRepository,ollehRepository,memberRepository);
//    }
//
//    @Test
//    @DisplayName("좋아요 테스트")
//    void testAddOlleh() {
//        // 사용자 등록
//        Member member = new Member("나래","12345","나래쨩",Gender.FEMALE);
//        when(memberRepository.findByUserName("나래")).thenReturn(Optional.of(member)); //해당 사용자를 조회할 수 있도록 findByUserName 사용
//
//        // 커뮤니티 등록
//        Community community = Community.of(new CommunityCreateRequest("일산","맛집","맛도리식당 가요!","맛도리를 찾아서",new String[]{"맛집", "존맛"}), member);
//        when(communityRepository.findById(1L)).thenReturn(Optional.of(community)); //해당 커뮤니티를 조회할 수 있도록 findById 사용
//
//        // Olleh(좋아요) 요청
//        String userName = "나래";
//        Long postId = 1L;
//        boolean result = communityService.addOlleh(userName, postId);
//
//        // 테스트 결과 확인
//        assertTrue(result);
//    }
//
//    @Test
//    @DisplayName("좋아요 취소 테스트")
//    void testRemoveOlleh() {
//        // 사용자 등록
//        Member member = new Member("나래","12345","나래쨩",Gender.FEMALE);
//
//        // 커뮤니티 등록
//        Community community = Community.of(new CommunityCreateRequest("일산","맛집","맛도리식당 가요!","맛도리를 찾아서",new String[]{"맛집", "존맛"}), member);
//
//        //olleh 추가
//        Olleh olleh = Olleh.of(member,community);
//
//        Mockito.when(ollehRepository.findByMemberAndCommunity(Mockito.any(Member.class), Mockito.any(Community.class)))
//                .thenReturn(java.util.Optional.of(olleh));
//
//        boolean result = communityService.removeOlleh(member, community);
//        assertTrue(result); // Optional 이므로 true 반환
//
//        // Olleh 제거 후 해당 Olleh가 삭제되었는지 확인
//        verify(ollehRepository).delete(olleh);
//    }
//}