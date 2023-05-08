package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.LocalCommunity;
import com.example.ollethboardproject.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalCommunityRepository extends JpaRepository<LocalCommunity, Long> {
    Optional<LocalCommunity> findByCommunityAndMember(Community community, Member member);
    Page<LocalCommunity> findByCommunity(Community community, Pageable pageable);

}

