package com.example.ollethboardproject.Service;

import com.example.ollethboardproject.controller.request.comment.CommentCreateRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.dto.CommentDTO;
import com.example.ollethboardproject.domain.entity.Comment;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.repository.CommentRepository;
import com.example.ollethboardproject.repository.PostRepository;
import com.example.ollethboardproject.repository.ReplyRepository;
import com.example.ollethboardproject.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private CommentService commentService;

    private Member member;
    private Post post;
    private Comment comment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        member = new Member("이름", "비번", "닉네임", Gender.MALE);
        post = new Post("제목", "게시글", member);
        comment = new  eComment("댓글", post, member);
    }

    @Test
    @DisplayName("댓글 생성 - 성공")
    public void createComment_Success() {
        // given
        CommentCreateRequest request = new CommentCreateRequest();
        String username = "username";
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new Member("이름", "비번", "닉넴", Gender.MALE), null
        );


        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(commentRepository.save(comment)).thenReturn(comment);

        // when
        CommentDTO commentDTO = commentService.createComment(1L, request, authentication);

        // then
        assertEquals(comment.getContent(), commentDTO.getContent());
    }

    @Test
    @DisplayName("댓글 조회 - 성공")
    public void getComment_Success() {
        // given
        Long commentId = 1L;
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // when
        CommentDTO commentDTO = commentService.getComment(commentId);

        // then
        assertEquals(comment.getContent(), commentDTO.getContent());
        assertEquals(comment.getMember().getId(),comment.getMember().getUsername(),comment.getMember().getNickName());
    }

    @Test
    @DisplayName("댓글 수정 - 성공")
    public void updateComment_Success() {
        // given

        // when
        // then
    }
}