package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.comment.CommentCreateRequest;
import com.example.ollethboardproject.controller.request.comment.CommentUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommentDTO;
import com.example.ollethboardproject.domain.entity.Comment;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.CommentRepository;
import com.example.ollethboardproject.repository.PostRepository;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentDTO createComment(Long postId, CommentCreateRequest commentCreateRequest, Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new OllehException(ErrorCode.POST_DOES_NOT_EXIST));

        Comment comment = new Comment();
        comment.setContent(commentCreateRequest.getContent());
        comment.setMember(member);
        comment.setPost(post);
        commentRepository.save(comment);

        return CommentDTO.fromEntity(comment);
    }

    public CommentDTO getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));
        return CommentDTO.fromEntity(comment);
    }

    public CommentDTO updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));

        comment.setContent(commentUpdateRequest.getContent());
        commentRepository.save(comment);

        return CommentDTO.fromEntity(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));

        commentRepository.delete(comment);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new OllehException(ErrorCode.POST_DOES_NOT_EXIST));

        List<Comment> comments = commentRepository.findByPost(post);

        List<CommentDTO> commentDTOs = comments.stream()
                .map(CommentDTO::fromEntity)
                .collect(Collectors.toList());

        return commentDTOs;
    }

    public List<CommentDTO> getCommentByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new OllehException(ErrorCode.POST_DOES_NOT_EXIST));

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(CommentDTO::fromEntity)
                .collect(Collectors.toList());
    }
}