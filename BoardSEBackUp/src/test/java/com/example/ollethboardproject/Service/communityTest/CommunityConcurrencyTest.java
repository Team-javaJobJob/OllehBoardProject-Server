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


}

