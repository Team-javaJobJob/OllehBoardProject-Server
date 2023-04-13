package com.example.securitypractice.service;

import com.example.securitypractice.domain.dto.Comment;
import com.example.securitypractice.domain.dto.Post;
import com.example.securitypractice.domain.entity.CommentEntity;
import com.example.securitypractice.domain.entity.LikeEntity;
import com.example.securitypractice.domain.entity.PostEntity;
import com.example.securitypractice.domain.entity.UserEntity;
import com.example.securitypractice.exception.AppException;
import com.example.securitypractice.exception.ErrorCode;
import com.example.securitypractice.repository.CommentRepository;
import com.example.securitypractice.repository.LikeRepository;
import com.example.securitypractice.repository.PostRepository;
import com.example.securitypractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void create(String title, String body, String userName) {
        UserEntity userEntity = getUserEntityByUserName(userName);

        PostEntity postEntity = PostEntity.of(title, body, userEntity);
        postRepository.save(postEntity);
    }

    @Transactional
    public void delete(Long postId, String userName) {

        UserEntity userEntity = getUserEntityByUserName(userName);
        PostEntity postEntity = getPostEntityById(postId);

        if (postEntity.getUserEntity().getId() != userEntity.getId()) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postRepository.delete(postEntity);

    }

    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::formEntity);
    }


    public Page<Post> my(String userName, Pageable pageable) {
        UserEntity userEntity = getUserEntityByUserName(userName);
        return postRepository.findAllByUserEntity(userEntity, pageable).map(Post::formEntity);

    }

    private PostEntity getPostEntityById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, String.format("post %s not founded", postId)));
    }

    private UserEntity getUserEntityByUserName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND, String.format("%s not found", userName)));
        return userEntity;
    }

    @Transactional
    public void like(Long postId, String userName) {
        UserEntity userEntity = getUserEntityByUserName(userName);
        PostEntity postEntity = getPostEntityById(postId);

        // 좋아요를 누른 상태에서 한번 더 누르면 취소
        if (dislike(userEntity, postEntity)) return;

        likeRepository.save(LikeEntity.of(userEntity, postEntity));
    }


    private boolean dislike(UserEntity userEntity, PostEntity postEntity) {
        if (likeRepository.findByUserEntityAndPostEntity(userEntity, postEntity).isPresent()) {
            LikeEntity likeEntity = likeRepository.findByUserEntityAndPostEntity(userEntity, postEntity).get();
            likeRepository.delete(likeEntity);
            return true;
        }
        return false;
    }

    public Integer likeCount(Long postId) {
        PostEntity postEntity = getPostEntityById(postId);
        return likeRepository.countByPostEntity(postEntity);

    }

    @Transactional
    public void createComment(Long postId, String userName, String comment) {
        UserEntity userEntity = getUserEntityByUserName(userName);
        PostEntity postEntity = getPostEntityById(postId);

        CommentEntity commentEntity = CommentEntity.of(userEntity, postEntity, comment);
        commentRepository.save(commentEntity);

    }

    @Transactional
    public Post modify(String title, String body, Long postId, String userName) {
        UserEntity userEntity = getUserEntityByUserName(userName);
        PostEntity postEntity = getPostEntityById(postId);

        if (postEntity.getUserEntity().getId() != userEntity.getId()) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }
        postEntity.setTitle(title);
        postEntity.setBody(body);
        PostEntity modifiedPostEntity = postRepository.save(postEntity);
        return Post.formEntity(modifiedPostEntity);

    }

    @Transactional
    public Comment modifyComment(Long postId, Long commentId, String userName, String comment) {

        UserEntity userEntity = getUserEntityByUserName(userName);
        PostEntity postEntity = getPostEntityById(postId);

        //특정 유저가 특정 게시물에 작성한 댓글들
        List<CommentEntity> commentEntities = commentRepository.findByUserEntityAndPostEntity(userEntity, postEntity).orElseThrow(() -> {
            throw new AppException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s can not find this comment", comment));
        });

        //댓글리스트중 특정 댓글 선택
        CommentEntity savedCommentEntity = commentEntities.stream()
                .filter(commentEntity -> commentEntity.getId() == commentId)
                .findAny()
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s can not find this comment", comment)));
        //선택된 댓글 수정
        savedCommentEntity.setComment(comment);
        CommentEntity modifiedCommentEntity = commentRepository.save(savedCommentEntity);

        return Comment.fromEntity(modifiedCommentEntity);

    }

    public void deleteComment(Long postId, Long commentId, String userName) {
        UserEntity userEntity = getUserEntityByUserName(userName);
        PostEntity postEntity = getPostEntityById(postId);

        List<CommentEntity> commentEntities = commentRepository.findByUserEntityAndPostEntity(userEntity, postEntity).orElseThrow(() -> {
            throw new AppException(ErrorCode.COMMENT_NOT_FOUND, "can not find this comment");
        });

        CommentEntity savedCommentEntity = commentEntities.stream()
                .filter(commentEntity -> commentEntity.getId() == commentId)
                .findAny()
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, "%s can not find this comment"));

        commentRepository.delete(savedCommentEntity);

    }

    ;
}
