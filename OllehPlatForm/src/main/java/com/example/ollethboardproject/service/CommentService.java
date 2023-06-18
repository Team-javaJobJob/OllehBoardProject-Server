package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.comment.CommentCreateRequest;
import com.example.ollethboardproject.controller.request.comment.CommentUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommentDTO;
import com.example.ollethboardproject.domain.dto.PostDTO;
import com.example.ollethboardproject.domain.entity.Comment;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.repository.CommentRepository;
import com.example.ollethboardproject.repository.PostRepository;
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
    private final MemberService memberService;
    private final PostRepository postRepository;
    private final ReplyService replyService;

    @Transactional
    public CommentDTO createComment(Long postId, CommentCreateRequest commentCreateRequest, Authentication authentication) {
        Member member = memberService.loadUserByUsername(authentication.getName());

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
    public CommentDTO updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest, Authentication authentication) {
        Member member = memberService.loadUserByUsername(authentication.getName());

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));

        // 댓글 작성자만 댓글 수정 가능
        validateMatches(comment, member);
        comment.update(commentUpdateRequest.getContent());
        commentRepository.save(comment);
        return CommentDTO.fromEntity(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Authentication authentication) {
        Member member = memberService.loadUserByUsername(authentication.getName());
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OllehException(ErrorCode.COMMENT_DOES_NOT_EXIST));

        // 댓글 작성자만 댓글 삭제 가능
        validateMatches(comment, member);
        deleteByComment(comment);
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

    private void deleteByComment(Comment comment) {
        replyService.deleteByCommentDTO(CommentDTO.fromEntity(comment));
        commentRepository.delete(comment);
    }

    public void deleteByPostDTO(PostDTO postDTO) {
        commentRepository.findByPostId(postDTO.getId()).forEach(comment -> {
            commentRepository.delete(comment);
        });
    }

    private void validateMatches(Comment comment, Member member) {
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new OllehException(ErrorCode.HAS_NOT_PERMISSION_TO_ACCESS);
        }
    }
}