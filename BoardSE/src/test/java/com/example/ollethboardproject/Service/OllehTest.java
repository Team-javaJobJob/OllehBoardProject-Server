package com.example.ollethboardproject.Service;

import com.example.ollethboardproject.controller.request.MemberJoinRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.Role;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Olleh;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.repository.MemberRepository;
import com.example.ollethboardproject.repository.OllehRepository;
import com.example.ollethboardproject.repository.PostRepository;
import com.example.ollethboardproject.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
public class OllehTest {

    @Autowired private PostService postService;
    @Autowired private PostRepository postRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private OllehRepository ollehRepository;

    @Test
    void testOlleh() {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest("나래", "password", "나래쨩", Gender.FEMALE, Role.ROLE_USER);
        Member member = memberRepository.save(Member.of(memberJoinRequest));
        Post post = postRepository.save(new Post("test title", "test content", member));

        // when
        postService.addOlleh(member.getUsername(), post.getId());


        // then
        Post savedPost = postRepository.findById(post.getId()).orElseThrow(EntityNotFoundException::new);
        assertThat(savedPost.getOllehsList().size()).isEqualTo(1);
    }


    @Test
    void testRemoveOlleh() {
        // given
        Member member = new Member("나래", "password", "나래쨩", Gender.FEMALE, Role.ROLE_USER);
        memberRepository.save(member);
        Post post = new Post("test title", "test content", member);
        postRepository.save(post);

        // post 에 Olleh (좋아요)
        postService.addOlleh(member.getUsername(), post.getId());

        // Olleh(좋아요)가 추가 되었는지 확인
        assertThat(postService.ollehCount(post.getId())).isEqualTo(1);

        // when
        boolean Result = postService.removeOlleh(member, post);

        // then
        assertThat(Result).isTrue();
        assertThat(postService.ollehCount(post.getId())).isEqualTo(0);
    }
}
