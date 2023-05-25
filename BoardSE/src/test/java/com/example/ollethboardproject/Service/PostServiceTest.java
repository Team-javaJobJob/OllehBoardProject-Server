package com.example.ollethboardproject.Service;

import com.example.ollethboardproject.controller.request.post.PostCreateRequest;
import com.example.ollethboardproject.controller.request.post.PostUpdateRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.repository.PostCountRepository;
import com.example.ollethboardproject.repository.PostRepository;
import com.example.ollethboardproject.service.PostService;
import com.example.ollethboardproject.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //모의 객체 초기화
        postService = new PostService(postRepository, postCountRepository);
    }

    @Test
    @DisplayName("게시물 생성 테스트")
    void testCreateBoard() {
        // 게시물 생성 요청
        PostCreateRequest postCreateRequest = new PostCreateRequest("테스트코드 어렵다", "그치만 우리팀 화이팅");
        Authentication authentication = Mockito.mock(Authentication.class);
        Member member = new Member("나래", "12345", "나래쨩", Gender.FEMALE);
        when(authentication.getPrincipal()).thenReturn(member);
        postService.createBoard(postCreateRequest, authentication);

        // postRepository 의 save 메서드 검증
        verify(postRepository).save(ArgumentMatchers.any(Post.class));
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    void testUpdateBoard() {
        // 게시물 수정 요청
        Long postId = 1L;
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("팀 자바잡잡", "화이팅");
        Authentication authentication = Mockito.mock(Authentication.class);
        Member member = new Member("나래", "12345", "나래쨩", Gender.FEMALE);
        Mockito.when(authentication.getPrincipal()).thenReturn(member);
        Mockito.when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(new Post("우리팀에", "다예님이 추가되었습니다", member)));
        postService.updateBoard(postId, postUpdateRequest, authentication);

        // postRepository 의 save 메서드 검증
        verify(postRepository).save(ArgumentMatchers.any(Post.class));
    }

    @Test
    @DisplayName("게시물 삭제 테스트")
    void testDeleteBoard() {
        // 게시물 삭제 요청
        Long postId = 1L;
        Authentication authentication = Mockito.mock(Authentication.class);
        Member member = new Member("나래", "12345", "나래쨩", Gender.FEMALE);
        Mockito.when(authentication.getPrincipal()).thenReturn(member);
        Mockito.when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(new Post("삭제될 게시물", "라랄랄랄", member)));
        postService.deleteBoard(postId, authentication);

        // delete 메서드 검증
        verify(postRepository).delete(ArgumentMatchers.any(Post.class));
    }

    @Test
    void 게시물_삭제시_작성자가_아니면_삭제할_수_없음() {
        // 게시물 작성자
        Member writer = new Member("나래", "12345", "내가 작성자다", Gender.FEMALE);

        // 다른 사용자
        Member otherUser = new Member("비실이 엄마", "12345", "작성자 아님", Gender.FEMALE);

        // 게시물 등록
        Long postId = 1L;
        Post post = new Post("네카라쿠배", "가고싶습니다", writer);

        // null authentication 객체를 생성
        Authentication authentication = null;

        // When
        postService.deleteBoard(postId, authentication);

        // Then
        // OllehException 발생-> 근데 왜 post does not exist 이 발생하지?
    }
}
