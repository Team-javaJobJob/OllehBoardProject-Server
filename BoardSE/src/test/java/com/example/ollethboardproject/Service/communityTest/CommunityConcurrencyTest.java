package com.example.ollethboardproject.Service.communityTest;

import com.example.ollethboardproject.controller.request.community.CommunityCreateRequest;
import com.example.ollethboardproject.controller.request.member.MemberJoinRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Olleh;
import com.example.ollethboardproject.repository.CommunityRepository;
import com.example.ollethboardproject.repository.MemberRepository;
import com.example.ollethboardproject.repository.OllehRepository;
import com.example.ollethboardproject.service.CommunityService;
import com.example.ollethboardproject.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommunityConcurrencyTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CommunityRepository communityRepository;
    @Autowired
    CommunityService communityService;
    @Autowired
    OllehRepository ollehRepository;


    @Test
    void name() throws InterruptedException {

        Community community = mock(Community.class);
        Olleh olleh = mock(Olleh.class);
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Member member = mock(Member.class);
            when(member.getUsername()).thenReturn("chanYoung" + i);
            members.add(member);
        }

        when(communityRepository.saveAndFlush(any(Community.class))).thenReturn(community);
        when(community.getId()).thenReturn(1L);
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));


        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            when(memberRepository.findByUserName(members.get(i).getUsername())).thenReturn(Optional.of(members.get(i)));
            when(communityRepository.findById(1L)).thenReturn(Optional.of(community));
            when(ollehRepository.findByMemberAndCommunity(members.get(i), community)).thenReturn(Optional.empty());
            when(ollehRepository.save(any(Olleh.class))).thenReturn(olleh);


            Member threadMember = members.get(i);
            executorService.submit(() -> {
                try {
                    System.out.println(threadMember.getUsername());
                    communityService.addOlleh(threadMember.getUsername(), community.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Integer ollehCount = communityService.ollehCount(1L);

        assertEquals(100, ollehCount);

    }

    @Test
    void fa() {
        Member member = mock(Member.class);
        Community community = mock(Community.class);
        Olleh olleh = mock(Olleh.class);

        when(memberRepository.findByUserName("chanYoung")).thenReturn(Optional.of(member));
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));
        when(ollehRepository.findByMemberAndCommunity(member, community)).thenReturn(Optional.empty());
        when(ollehRepository.save(Olleh.of(member, community))).thenReturn(any(Olleh.class));

        // Mock the behavior of removeOlleh method

        boolean result = communityService.addOlleh("chanYoung", 1L);
        //     테스트 검증
        assertTrue(result);
    }

    @Test
    void ad() throws InterruptedException {
        String kewords[] = {"a", "a", "A"};
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Member member = Member.of(new MemberJoinRequest("a" + i, "A", "a", Gender.MALE));
            memberRepository.save(member);
            members.add(member);
        }
        Community community = communityRepository.save(Community.of(new CommunityCreateRequest(
                "1", "1", "1", "1", kewords), members.get(0)));

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {

            Member threadMember = members.get(i);
            executorService.submit(() -> {
                try {
                    System.out.println(threadMember.getUsername());
                    communityService.addOlleh(threadMember.getUsername(),community.getId());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Integer ollehCount = communityService.ollehCount(1L);

        assertEquals(100, ollehCount);
    }

    @Test
    void tata() throws InterruptedException {
        Community community = mock(Community.class);
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Member member = mock(Member.class);
            when(member.getUsername()).thenReturn("chanYoung" + i);
            members.add(member);
        }

        when(communityRepository.saveAndFlush(any(Community.class))).thenReturn(community);
        when(community.getId()).thenReturn(1L);
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            when(memberRepository.findByUserName(members.get(i).getUsername())).thenReturn(Optional.of(members.get(i)));
            when(communityRepository.findById(1L)).thenReturn(Optional.of(community));

            when(ollehRepository.findByMemberAndCommunity(members.get(i), community)).thenReturn(Optional.empty());
            when(ollehRepository.save(any(Olleh.class))).thenAnswer(invocation -> {
                Olleh savedOlleh = invocation.getArgument(0);
                // 실제 Olleh 객체 생성 후 반환
                return Olleh.of(savedOlleh.getMember(), savedOlleh.getCommunity());
            });

            Member threadMember = members.get(i);
            executorService.submit(() -> {
                try {
                    System.out.println(threadMember.getUsername());
                    communityService.addOlleh(threadMember.getUsername(), community.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Integer ollehCount = communityService.ollehCount(1L);

        assertEquals(100, ollehCount);
    }
}

