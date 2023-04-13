package com.example.securitypractice.repository;

import com.example.securitypractice.domain.entity.CommentEntity;
import com.example.securitypractice.domain.entity.LikeEntity;
import com.example.securitypractice.domain.entity.PostEntity;
import com.example.securitypractice.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<List<CommentEntity>> findByUserEntityAndPostEntity(UserEntity userEntity, PostEntity postEntity);
}
