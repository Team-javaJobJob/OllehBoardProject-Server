package com.example.securitypractice.repository;

import com.example.securitypractice.domain.dto.Post;
import com.example.securitypractice.domain.entity.PostEntity;
import com.example.securitypractice.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAllByUserEntity(UserEntity userEntity, Pageable pageable);


}
