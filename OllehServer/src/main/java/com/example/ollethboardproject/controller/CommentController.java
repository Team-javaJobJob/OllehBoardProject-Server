package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.comment.CommentCreateRequest;
import com.example.ollethboardproject.controller.request.comment.CommentUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommentDTO;
import com.example.ollethboardproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") Long commentId) {
        CommentDTO comment = commentService.getComment(commentId);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable("postId") Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentCreateRequest createRequest, Authentication authentication) {
        CommentDTO createdComment = commentService.createComment(createRequest, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("commentId") Long commentId,
                                                    @RequestBody CommentUpdateRequest updateRequest, Authentication authentication) {
        CommentDTO updatedComment = commentService.updateComment(commentId, updateRequest, authentication);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId, Authentication authentication) {
        // 댓글 삭제 메서드 호출
        commentService.deleteComment(commentId, authentication);
        return ResponseEntity.noContent().build();
    }
}