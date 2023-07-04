package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.reply.ReplyCreateRequest;
import com.example.ollethboardproject.controller.request.reply.ReplyUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommentDTO;
import com.example.ollethboardproject.domain.dto.PostDTO;
import com.example.ollethboardproject.domain.dto.ReplyDTO;
import com.example.ollethboardproject.domain.entity.Comment;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.domain.entity.Reply;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.CommentRepository;
import com.example.ollethboardproject.repository.PostRepository;
import com.example.ollethboardproject.repository.ReplyRepository;
import com.example.ollethboardproject.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<ReplyDTO> getRepliesByComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));
        List<Reply> replies = comment.getReplies();
        return ReplyDTO.fromEntityList(replies);
    }

    public ReplyDTO createReply(ReplyCreateRequest createRequest, Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();

        Post post = postRepository.findById(createRequest.getPostId()).orElseThrow(
                () -> new OllehException(ErrorCode.POST_DOES_NOT_EXIST));

        Comment comment = commentRepository.findById(createRequest.getCommentId()).orElseThrow(
                () -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));

        Reply reply = Reply.of(createRequest, member, post, comment);
        replyRepository.save(reply);
        return ReplyDTO.fromEntity(reply);
    }

    public ReplyDTO updateReply(Long replyId, ReplyUpdateRequest updateRequest, Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();

        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new OllehException(ErrorCode.REPLY_DOES_NOT_EXIST));

        // 대댓글 작성자만 게시물 수정 가능
        validateMatches(reply, member);
        reply.update(updateRequest.getContent());
        replyRepository.save(reply);
        return ReplyDTO.fromEntity(reply);
    }

    public void deleteReply(Long replyId, Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();

        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new OllehException(ErrorCode.REPLY_DOES_NOT_EXIST));

        // 대댓글 작성자만 게시물 삭제 가능
        validateMatches(reply, member);
        replyRepository.delete(reply);
    }


    public ReplyDTO getReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new OllehException(ErrorCode.REPLY_DOES_NOT_EXIST));
        return ReplyDTO.fromEntity(reply);
    }

    public void deleteByPostDTO(PostDTO postDTO) {
        replyRepository.findByPostId(postDTO.getId()).forEach(reply -> {
            replyRepository.delete(reply);
        });
    }

    public void deleteByCommentDTO(CommentDTO commentDTO) {
        replyRepository.findByParentCommentId(commentDTO.getId()).forEach(reply -> {
            replyRepository.delete(reply);
        });
    }

    private void validateMatches(Reply reply, Member member) {
        if (!reply.getMember().getId().equals(member.getId())) {
            throw new OllehException(ErrorCode.PERMISSION_DENIED);
        }
    }
}