package com.example.securitypractice.repository;

import com.example.securitypractice.domain.entity.LikeEntity;
import com.example.securitypractice.domain.entity.PostEntity;
import com.example.securitypractice.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserEntityAndPostEntity(UserEntity userEntity, PostEntity postEntity);

    @Query("SELECT COUNT(*) FROM LikeEntity entity WHERE entity.postEntity =:post")
    Integer countByPostEntity(@Param("post") PostEntity postEntity);
}
