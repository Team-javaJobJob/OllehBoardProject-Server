package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.CommunityMember;
import com.example.ollethboardproject.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
    Optional<CommunityMember> findByCommunityAndMember(Community community, Member member);

    Page<CommunityMember> findByCommunity(Community community, Pageable pageable);

    Optional<List<CommunityMember>> findByMember(Member member);

    CommunityMember findCommunityMemberByCommunityId(Long communityId);
}