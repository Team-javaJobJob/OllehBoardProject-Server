package com.example.ollethboardproject.controller.response;

import com.example.ollethboardproject.controller.request.reply.ReplyCreateRequest;
import com.example.ollethboardproject.controller.request.reply.ReplyUpdateRequest;
import com.example.ollethboardproject.domain.dto.ReplyDTO;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.common.reflection.XMember;
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
    public ResponseEntity<ReplyDTO> getReply(@PathVariable("replyId") Long replyId) {
        ReplyDTO reply = replyService.getReply(replyId);
        return ResponseEntity.ok(reply);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<ReplyDTO>> getRepliesByComment(@PathVariable("commentId") Long commentId) {
        List<ReplyDTO> replies = replyService.getRepliesByComment(commentId);
        return ResponseEntity.ok(replies);
    }

    @PostMapping
    public ResponseEntity<ReplyDTO> createReply(@RequestBody ReplyCreateRequest createRequest) {
        ReplyDTO createdReply = replyService.createReply(createRequest.getPostId(), createRequest.getCommentId(), createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReply);
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<ReplyDTO> updateReply(
            @PathVariable("replyId") Long replyId,
            @RequestBody ReplyUpdateRequest updateRequest
    ) {
        ReplyDTO updatedReply = replyService.updateReply(replyId, updateRequest);
        return ResponseEntity.ok(updatedReply);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable("replyId") Long replyId, Authentication authentication) {

        // 인증된 사용자 정보 가져오기
        Member user = (Member) authentication.getPrincipal();

        // 대댓글 삭제 메서드 호출
        replyService.deleteReply(replyId, user);
        return ResponseEntity.noContent().build();
    }
}