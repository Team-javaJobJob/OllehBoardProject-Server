package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.reply.ReplyCreateRequest;
import com.example.ollethboardproject.controller.request.reply.ReplyUpdateRequest;
import com.example.ollethboardproject.domain.dto.ReplyDTO;
import com.example.ollethboardproject.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyDTO> getReply(@PathVariable Long replyId) {
        ReplyDTO reply = replyService.getReply(replyId);
        return ResponseEntity.ok(reply);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<ReplyDTO>> getRepliesByComment(@PathVariable Long commentId) {
        List<ReplyDTO> replies = replyService.getRepliesByComment(commentId);
        return ResponseEntity.ok(replies);
    }

    @PostMapping
    public ResponseEntity<ReplyDTO> createReply(@RequestBody ReplyCreateRequest createRequest, Authentication authentication) {
        ReplyDTO createdReply = replyService.createReply(createRequest, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReply);
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<ReplyDTO> updateReply(@PathVariable Long replyId, @RequestBody ReplyUpdateRequest updateRequest) {
        ReplyDTO updatedReply = replyService.updateReply(replyId, updateRequest);
        return ResponseEntity.ok(updatedReply);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long replyId, Authentication authentication) {
        // 대댓글 삭제 메서드 호출
        replyService.deleteReply(replyId, authentication);
        return ResponseEntity.noContent().build();
    }
}