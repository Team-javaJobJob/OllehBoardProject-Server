package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.post.PostCreateRequest;
import com.example.ollethboardproject.controller.request.post.PostUpdateRequest;
import com.example.ollethboardproject.controller.response.Response;
import com.example.ollethboardproject.domain.dto.PostCountDTO;
import com.example.ollethboardproject.domain.dto.PostDTO;
import com.example.ollethboardproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class PostController {

    private final PostService postService;

    //보드 전체 조회
    //TODO: LIST -> pageable 로 변환하기
    @GetMapping("/list")
    public ResponseEntity<List<PostDTO>> findAllPost(@PageableDefault(size = 10)Pageable pageable) {
        log.info("GET /api/v1/boards/list");
        List<PostDTO> postDTOList = postService.findAllBoards(Pageable.unpaged());
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication) {
        log.info("POST /api/v1/boards");
        PostDTO createdPostDTO = postService.createBoard(postCreateRequest, authentication);
        return new ResponseEntity<>(createdPostDTO, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest postUpdateRequest ,Authentication authentication) {
        log.info("PUT /api/v1/boards/{}", id);
        PostDTO updatedPostDTO = postService.updateBoard(id, postUpdateRequest, authentication);
        return new ResponseEntity<>(updatedPostDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        log.info("DELETE /api/v1/boards/{}", id);
        postService.deleteBoard(id, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //좋아요(=올래)
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{postId}/olleh")
    public Response<Void> olleh(@PathVariable Long postId, Authentication authentication){
        postService.addOlleh(authentication.getName(), postId);
        return Response.success();
    }

    //좋아요수
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{postId}/olleh")
    public Response<Integer> olleh(@PathVariable Long postId){
        Integer ollehCount = postService.ollehCount(postId); //postService 의 ollehCount 메소드를 호출 postId에 해당하는 Post 객체의 Olleh 개수 가져옴
        return Response.success(ollehCount); //ollehCount 값을 Response 객체에 담아서 반환
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCountDTO> findPostById(@PathVariable Long id, Authentication authentication) {
        log.info("GET /api/v1/post/{}", id);
        PostCountDTO postCountDTO = postService.findBoardById(id, authentication);
        return new ResponseEntity<>(postCountDTO, HttpStatus.OK);
    }


}

