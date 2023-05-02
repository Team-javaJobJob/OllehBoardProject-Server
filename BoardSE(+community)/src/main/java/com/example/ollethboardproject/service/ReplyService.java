package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.ReplyCreateRequest;
import com.example.ollethboardproject.controller.request.ReplyUpdateRequest;
import com.example.ollethboardproject.domain.dto.ReplyDTO;
import com.example.ollethboardproject.domain.entity.Comment;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.domain.entity.Reply;
import com.example.ollethboardproject.exception.BoardException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.CommentRepository;
import com.example.ollethboardproject.repository.PostRepository;
import com.example.ollethboardproject.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<ReplyDTO> getRepliesByComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BoardException(ErrorCode.COMMENT_DOES_NOT_EXIST));
        List<Reply> replies = comment.getReplies();
        //TODO List 값 넣기
        return ReplyDTO.fromEntityList(replies);
    }

    public ReplyDTO createReply(Long postId, Long commentId, ReplyCreateRequest createRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new BoardException(ErrorCode.POST_DOES_NOT_EXIST));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BoardException(ErrorCode.COMMENT_DOES_NOT_EXIST));
        Member author = createRequest.getAuthor();
        Reply reply = Reply.of(createRequest, author, post, comment);
        Reply savedReply = replyRepository.save(reply);
        return ReplyDTO.fromEntity(savedReply);
    }

    public ReplyDTO updateReply(Long replyId, ReplyUpdateRequest updateRequest) {
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        Reply reply = replyOptional.orElseThrow(() -> new BoardException(ErrorCode.REPLY_DOES_NOT_EXIST));
        reply.update(updateRequest.getContent());
        Reply updatedReply = replyRepository.save(reply);
        return ReplyDTO.fromEntity(updatedReply);
    }

    public void deleteReply(Long replyId) {
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        Reply reply = replyOptional.orElseThrow(() -> new BoardException(ErrorCode.REPLY_DOES_NOT_EXIST));
        replyRepository.delete(reply);
    }

    public ReplyDTO getReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new BoardException(ErrorCode.REPLY_DOES_NOT_EXIST));
        return ReplyDTO.fromEntity(reply);
    }
}

