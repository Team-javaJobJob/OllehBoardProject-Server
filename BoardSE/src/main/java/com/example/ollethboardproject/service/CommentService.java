package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.comment.CommentCreateRequest;
import com.example.ollethboardproject.controller.request.comment.CommentUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommentDTO;
import com.example.ollethboardproject.domain.entity.Comment;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.repository.CommentRepository;
import com.example.ollethboardproject.repository.PostRepository;
import com.example.ollethboardproject.repository.ReplyRepository;
import com.example.ollethboardproject.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public CommentDTO createComment(Long postId, CommentCreateRequest commentCreateRequest, Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new OllehException(ErrorCode.POST_DOES_NOT_EXIST));


        Comment comment = Comment.of(commentCreateRequest.getContent(), post, member);
        commentRepository.save(comment);


        return CommentDTO.fromEntity(comment);
    }

    @Transactional(readOnly = true)
    public CommentDTO getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));
        return CommentDTO.fromEntity(comment);
    }

    @Transactional
    public CommentDTO updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));

        comment.update(commentUpdateRequest.getContent());
        return CommentDTO.fromEntity(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));

        if (comment.getMember() != member) {
            throw new OllehException(ErrorCode.HAS_NOT_PERMISSION_TO_ACCESS);
        }

        replyRepository.findByParentComment(comment).forEach(reply -> {
            replyRepository.delete(reply);
        });

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new OllehException(ErrorCode.POST_DOES_NOT_EXIST));

        List<Comment> comments = commentRepository.findByPost(post);

        List<CommentDTO> commentDTOs = comments.stream()
                .map(CommentDTO::fromEntity)
                .collect(Collectors.toList());

        return commentDTOs;
    }

}