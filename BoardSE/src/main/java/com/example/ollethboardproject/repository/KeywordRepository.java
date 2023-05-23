package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Community> findCommunityByKeyword(String keyword);

    List<Keyword> findByKeyword(String keyword);
}
