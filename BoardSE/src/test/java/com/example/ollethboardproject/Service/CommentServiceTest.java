package com.example.ollethboardproject.Service;

import com.example.ollethboardproject.domain.entity.Comment;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.repository.CommentRepository;
import com.example.ollethboardproject.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServiceTest {
    private CommentService commentService;
    private CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        commentRepository = new CommentRepository();
        commentService = new CommentService(commentRepository);
    }

    @Test
    public void testAddComment() {
        // given
        Long memberId = 1L;
        Member author = new Member(memberId, "이름", "닉네임");
        Comment comment = new Comment("Test comment", author);

        // when
        Comment savedComment = commentService.addComment(comment);

        // then
        assertNotNull(savedComment.getId());
        assertEquals(comment.getContent(), savedComment.getContent());
        assertEquals(comment.getAuthor(), savedComment.getAuthor());
        assertEquals(memberId, savedComment.getAuthor().getId());
    }

    @Test
    public void testGetCommentById() {
        // given
        Long commentId = 1L;
        Long memberId = 1L;
        Member author = new Member(memberId, "johndoe", "John Doe");
        Comment comment = new Comment(commentId, "Test comment", author);
        commentRepository.save(comment);

        // when
        Comment retrievedComment = commentService.getCommentById(commentId);

        // then
        assertEquals(comment, retrievedComment);
    }

    @Test
    public void testUpdateComment() {
        // given
        Long commentId = 1L;
        Long memberId = 1L;
        Member author = new Member(memberId, "johndoe", "John Doe");
        Comment comment = new Comment(commentId, "Test comment", author);
        commentRepository.save(comment);

        Comment updatedComment = new Comment(commentId, "Updated comment", author);

        // when
        Comment savedComment = commentService.updateComment(updatedComment);

        // then
        assertEquals(updatedComment.getContent(), savedComment.getContent());
    }

    @Test
    public void testDeleteComment() {
        // given
        Long commentId = 1L;
        Long memberId = 1L;
        Member author = new Member(memberId, "johndoe", "John Doe");
        Comment comment = new Comment(commentId, "Test comment", author);
        commentRepository.save(comment);

        // when
        commentService.deleteComment(commentId);

        // then
        assertNull(commentRepository.findById(commentId));
    }

}
