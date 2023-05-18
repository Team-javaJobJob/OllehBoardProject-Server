package com.example.ollethboardproject.Service;

import com.example.ollethboardproject.controller.request.post.PostCreateRequest;
import com.example.ollethboardproject.controller.request.post.PostUpdateRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Olleh;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.repository.MemberRepository;
import com.example.ollethboardproject.repository.OllehRepository;
import com.example.ollethboardproject.repository.PostCountRepository;
import com.example.ollethboardproject.repository.PostRepository;
import com.example.ollethboardproject.service.PostService;
import com.example.ollethboardproject.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
public class PostServiceTest {

    @Mock private PostRepository postRepository;
    @Mock private PostCountRepository postCountRepository;
    @Mock private OllehRepository ollehRepository;
    @Mock private MemberRepository memberRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //모의 객체 초기화
        postService = new PostService(postRepository, postCountRepository, ollehRepository, memberRepository);
    }

    @Test
    @DisplayName("게시물 생성 테스트")
    void testCreateBoard(){
        // 게시물 생성 요청
        PostCreateRequest postCreateRequest = new PostCreateRequest("테스트코드 어렵다","그치만 우리팀 화이팅");
        Authentication authentication = Mockito.mock(Authentication.class);
        Member member = new Member("나래","12345","나래쨩",Gender.FEMALE);
        when(authentication.getPrincipal()).thenReturn(member);
        postService.createBoard(postCreateRequest, authentication);

        // postRepository 의 save 메서드 검증
        verify(postRepository).save(ArgumentMatchers.any(Post.class));
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    void testUpdateBoard(){
        // 게시물 수정 요청
        Long postId = 1L;
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("팀 자바잡잡","화이팅");
        Authentication authentication = Mockito.mock(Authentication.class);
        Member member = new Member("나래","12345","나래쨩",Gender.FEMALE);
        Mockito.when(authentication.getPrincipal()).thenReturn(member);
        Mockito.when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(new Post("우리팀에","다예님이 추가되었습니다",member)));
        postService.updateBoard(postId, postUpdateRequest, authentication);

        // postRepository 의 .save 메서드 검증
        verify(postRepository).save(ArgumentMatchers.any(Post.class));
    }

    @Test
    @DisplayName("게시물 삭제 테스트")
    void testDeleteBoard(){
        // 게시물 삭제 요청
        Long postId = 1L;
        Authentication authentication = Mockito.mock(Authentication.class);
        Member member = new Member("나래","12345","나래쨩",Gender.FEMALE);
        Mockito.when(authentication.getPrincipal()).thenReturn(member);
        Mockito.when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(new Post("삭제될 게시물","라랄랄랄",member)));
        postService.deleteBoard(postId, authentication);

        // delete 메서드 검증
        verify(postRepository).delete(ArgumentMatchers.any(Post.class));
    }

    @Test
    void 게시물_삭제시_작성자가_아니면_삭제할_수_없음(){
        // 게시물 작성자
        Member writer = new Member("나래", "12345", "내가 작성자다", Gender.FEMALE);

        // 다른 사용자
        Member otherUser = new Member("비실이 엄마", "12345", "작성자 아님", Gender.FEMALE);

        // 게시물 등록
        Long postId = 1L;
        Post post = new Post("네카라쿠배", "가고싶습니다", writer);

        // Mock 인증 객체 생성
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(otherUser);

        // 게시물 조회 Mock 설정
        Mockito.when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(post));

        // 삭제 요청 시 예외가 발생
        assertThrows(OllehException.class, () -> postService.deleteBoard(postId, authentication));
        //메서드에 throws OllehException 을 추가 해서 제대로 예외가 발생되는지 체크

        // delete 메서드가 호출되지 않은 것을 검증
        verify(postRepository, never()).delete(post);
    }

    @Test
    @DisplayName("좋아요 테스트")
    void testAddOlleh() {
        // 사용자 등록
        Member member = new Member("나래","12345","나래쨩",Gender.FEMALE);
        when(memberRepository.findByUserName("나래")).thenReturn(Optional.of(member)); //해당 사용자를 조회할 수 있도록 findByUserName 사용

        // 게시물 등록
        Post post = new Post("조아요","테스트를 해보자",member);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post)); //해당 게시물을 조회할 수 있도록 findById 사용

        // Olleh(좋아요) 요청
        String userName = "나래";
        Long postId = 1L;
        boolean result = postService.addOlleh(userName, postId);

        // 테스트 결과 확인
        assertTrue(result);
    }

    @Test
    @DisplayName("좋아요 취소 테스트")
    void testRemoveOlleh() {
        // 사용자 등록
        Member member = new Member("나래","12345","나래쨩",Gender.FEMALE);

        // 게시물 등록
        Post post = new Post("조아요 취소","테스트를 해보자", member);

        //olleh 추가
        Olleh olleh = Olleh.of(member,post);

        Mockito.when(ollehRepository.findByMemberAndPost(Mockito.any(Member.class), Mockito.any(Post.class)))
                .thenReturn(java.util.Optional.of(olleh));

        boolean result = postService.removeOlleh(member, post);
        assertTrue(result); // Optional 이므로 true 반환

        // Olleh 제거 후 해당 Olleh가 삭제되었는지 확인
        verify(ollehRepository).delete(olleh);
    }
}
