package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface
CommunityRepository extends JpaRepository<Community, Long> {

    //TODO: List 바꾸기
    Optional<Community> findByCommunityName(String communityName);

    //최신순 정렬
    @Query("SELECT c FROM Community c ORDER BY c.createdAt DESC") //모든 커뮤니티 조회 ->createdAt 필드를 기준으로 내림차순 정렬
    List<Community> findAllByOrderByCreatedAtDesc();

    //추천순 (올레순) 정렬
    @Query("SELECT c FROM Community c ORDER BY SIZE(c.ollehsList) DESC")//모든 커뮤니티 조회 ->ollehList 의 크기를 기준으로 내림차순 정렬
    List<Community> findAllByOrderByOllehDesc();
}
