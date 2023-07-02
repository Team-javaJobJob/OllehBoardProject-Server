
package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByPostId(Long postId);
    List<Reply> findByParentCommentId(Long ParentCommentId);
}