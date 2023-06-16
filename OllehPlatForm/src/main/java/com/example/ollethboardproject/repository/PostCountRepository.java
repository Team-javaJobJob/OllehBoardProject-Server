package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.domain.entity.PostCount;
import com.example.ollethboardproject.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCountRepository extends JpaRepository<PostCount, Long> {
    Optional<PostCount> findByMemberAndPost(Member member, Post post);
    Integer countByPost(Post post);
}
