package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.post.PostCreateRequest;
import com.example.ollethboardproject.controller.request.post.PostUpdateRequest;
import com.example.ollethboardproject.domain.dto.PostCountDTO;
import com.example.ollethboardproject.domain.dto.PostDTO;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.domain.entity.PostCount;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.MemberRepository;
import com.example.ollethboardproject.repository.PostCountRepository;
import com.example.ollethboardproject.repository.PostRepository;
import com.example.ollethboardproject.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCountRepository postCountRepository;

    public List<PostDTO> findAllPost() {
        //TODO: LIST -> pageable
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToPostDto).collect(Collectors.toList());
    }

    public PostCountDTO findPostById(Long postId, Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        Post post = postRepository.findById(postId).orElseThrow(() -> new OllehException(ErrorCode.POST_DOES_NOT_EXIST));
        //조회수 누적 및 조회  (유저가 동일한 게시물을 조회한다면 해당 게시물의 조회수가 누적되지 않는다 )
        Integer countByBoard = getPostCount(member, post);
        //위의 getBoardCount에서 해당 컬럼이 존재하지않는다면 그 값을 저장했으므로 바로 엔티티를  extract 시킴
        PostCount postCount = postCountRepository.findByMemberAndPost(member, post).get();
        return mapToPostCountDto(countByBoard, postCount);
    }

    public PostDTO createPost(PostCreateRequest postCreateRequest, Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        Post post = Post.of(postCreateRequest, member);
        postRepository.save(post);
        return mapToPostDto(post);
    }

    public PostDTO updatePost(Long id, PostUpdateRequest postUpdateRequest, Authentication authentication) {
        //게시물이 존재하지 않는다면 예외 발생
        Post post = postRepository.findById(id).orElseThrow(() -> new OllehException(ErrorCode.POST_DOES_NOT_EXIST));
        //캐스팅에 의한 에러가 나지 않도록 ClassUtil 메서드 사용
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        //게시물 작성자만 게시물을 수정할 수 있다.
        validateMatches(post, member);
        //게시물 수정 (Setter 를 사용하지 않기 위함)
        post.update(postUpdateRequest, member);
        //게시물 저장
        postRepository.save(post);
        return mapToPostDto(post);
    }

    public void deletePost(Long id, Authentication authentication) {
        Post post = postRepository.findById(id).orElseThrow(() -> new OllehException(ErrorCode.POST_DOES_NOT_EXIST));
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        //게시물 작성자만 게시물을 삭제할 수 있다.
        validateMatches(post, member);
        postRepository.delete(post);
    }

    private Integer getPostCount(Member member, Post post) {
        saveCount(member, post);
        return postCountRepository.countByPost(post);
    }

    private void saveCount(Member member, Post post) {
        if (postCountRepository.findByMemberAndPost(member, post).isEmpty()) {
            postCountRepository.save(PostCount.of(member, post));
        }
    }

    private void validateMatches(Post post, Member member) {
        if (post.getMember().getId() != member.getId()) {
            throw new OllehException(ErrorCode.HAS_NOT_PERMISSION_TO_ACCESS);
        }
    }

    private PostDTO mapToPostDto(Post post) {
        return PostDTO.fromEntity(post);
    }

    private PostCountDTO mapToPostCountDto(Integer countByBoard, PostCount postCount) {
        return PostCountDTO.of(countByBoard, postCount);
    }
}