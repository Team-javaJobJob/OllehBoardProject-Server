package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.PostCreateRequest;
import com.example.ollethboardproject.controller.request.PostUpdateRequest;
import com.example.ollethboardproject.domain.dto.PostCountDTO;
import com.example.ollethboardproject.domain.dto.PostDTO;
import com.example.ollethboardproject.domain.entity.Olleh;
import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.domain.entity.PostCount;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.exception.BoardException;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.MemberRepository;
import com.example.ollethboardproject.repository.OllehRepository;
import com.example.ollethboardproject.repository.PostCountRepository;
import com.example.ollethboardproject.repository.PostRepository;
import com.example.ollethboardproject.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCountRepository postCountRepository;
    private final OllehRepository ollehRepository;
    private final MemberRepository memberRepository;

    public List<PostDTO> findAllBoards() {
        //TODO: LIST -> pageable
        Pageable pageable = PageRequest.ofSize(findAllBoards().size());
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToPostDto).collect(Collectors.toList());
    }

    public PostCountDTO findBoardById(Long postId, Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        Post post = postRepository.findById(postId).orElseThrow(() -> new BoardException(ErrorCode.POST_DOES_NOT_EXIST));
        //조회수 누적 및 조회  (유저가 동일한 게시물을 조회한다면 해당 게시물의 조회수가 누적되지 않는다 )
        Integer countByBoard = getPostCount(member, post);
        //위의 getBoardCount에서 해당 컬럼이 존재하지않는다면 그 값을 저장했으므로 바로 엔티티를  extract 시킴
        PostCount postCount = postCountRepository.findByMemberAndPost(member, post).get();
        return mapToPostCountDto(countByBoard, postCount);
    }

    public PostDTO createBoard(PostCreateRequest postCreateRequest, Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        Post post = Post.of(postCreateRequest, member);
        postRepository.save(post);
        return mapToPostDto(post);
    }

    public PostDTO updateBoard(Long id, PostUpdateRequest postUpdateRequest, Authentication authentication) {
        //게시물이 존재하지 않는다면 예외 발생
        Post post = postRepository.findById(id).orElseThrow(() -> new BoardException(ErrorCode.POST_DOES_NOT_EXIST));
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

    public void deleteBoard(Long id, Authentication authentication) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BoardException(ErrorCode.POST_DOES_NOT_EXIST));
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
            throw new BoardException(ErrorCode.HAS_NOT_PERMISSION_TO_ACCESS);
        }
    }

    private PostDTO mapToPostDto(Post post) {
        return PostDTO.fromEntity(post);
    }

    private PostCountDTO mapToPostCountDto(Integer countByBoard, PostCount postCount) {
        return PostCountDTO.of(countByBoard, postCount);
    }

    //Olleh(좋아요)

    private Member getMemberByMemberName(String userName){ //userName 을 인자로 받아 member 를 조회하고 존재하지 않으면 BoardException 발생
        return memberRepository.findByUserName(userName).orElseThrow(()->
                new BoardException(ErrorCode.USER_NOT_FOUND));
    }

    private Post getPostById(Long postId){ //postId 을 인자로 받아 Post 를 조회하고 존재하지 않으면 BoardException 발생
        return postRepository.findById(postId).orElseThrow(() ->
                new BoardException(ErrorCode.POST_DOES_NOT_EXIST));
    }

    @Transactional //하나의 트랜잭션으로 묶어서 하나라도 실패하면 모두 롤백
    public void addOlleh(String userName, Long postId){
        Member member = getMemberByMemberName(userName); //getMemberByMemberName 메서드 호출하여 userName 에 해당하는 member 를 가져옴
        Post post = getPostById(postId); //getPostById 메서드 호출하여 postI 에 해당하는 post 를 가져옴

        if (removeOlleh(member, post)) return; //removeOlleh 호출-> member,post 인자로 받아서 Olleh 객체삭제 -> true 반환시 실행 중지

        ollehRepository.save(Olleh.of(member, post));
    }

    public boolean removeOlleh(Member member, Post post) {
        if(ollehRepository.findByMemberAndPost(member, post).isPresent()){ //isPresent() 메소드로 Optional 객체에 값이 있는지 확인
            Olleh olleh = ollehRepository.findByMemberAndPost(member, post).get(); //값이 있다면 get()으로 가져옴
            ollehRepository.delete(olleh); //가져온 값을 <-ollehRepository.delete(olleh)메서드로 삭제
            return true; //그리고 true 반환 (삭제 성공시 true 반환)
        }
        return false; //삭제할 객체가 없으면 false 반환
    }

    public Integer ollehCount(Long postId) {
        Post post = getPostById(postId); //postId 에 해당하는 post 객체를 가져옴
        return ollehRepository.countByPost(post); //post 객체와 연관된 Olleh 객체의 개수 반환
    }
}