package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    Optional<Community> findByCommunityName(String communityName);

}
