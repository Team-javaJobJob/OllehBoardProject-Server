package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Comment;
import com.example.ollethboardproject.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
