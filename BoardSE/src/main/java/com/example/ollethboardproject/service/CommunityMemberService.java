package com.example.ollethboardproject.service;

import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.CommunityMember;
import com.example.ollethboardproject.repository.CommunityMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityMemberService {
    private final CommunityMemberRepository communityMemberRepository;

    public void deleteCommunityMemberByCommunity(Community community) {
        CommunityMember communityMember = communityMemberRepository.findCommunityMemberByCommunityId(community.getId());
        communityMemberRepository.delete(communityMember);
    }
}