
package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.post.PostCreateRequest;
import com.example.ollethboardproject.controller.request.post.PostUpdateRequest;
import com.example.ollethboardproject.domain.dto.PostCountDTO;
import com.example.ollethboardproject.domain.dto.PostDTO;
import com.example.ollethboardproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    //보드 전체 조회
    //TODO: LIST -> pageable 로 변환하기
    @GetMapping("")
    public ResponseEntity<List<PostDTO>> findAllPost() {
        log.info("GET /api/v1/post");
        List<PostDTO> postDTOList = postService.findAllPost();
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCountDTO> findPostById(@PathVariable Long id, Authentication authentication) {
        log.info("GET /api/v1/post/{}", id);
        PostCountDTO postCountDTO = postService.findPostById(id, authentication);
        return new ResponseEntity<>(postCountDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication) {
        log.info("POST /api/v1/post");
        PostDTO createdPostDTO = postService.createPost(postCreateRequest, authentication);
        return new ResponseEntity<>(createdPostDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest postUpdateRequest, Authentication authentication) {
        log.info("PUT /api/v1/post/{}", id);
        PostDTO updatedPostDTO = postService.updatePost(id, postUpdateRequest, authentication);
        return new ResponseEntity<>(updatedPostDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        log.info("DELETE /api/v1/post/{}", id);
        postService.deletePost(id, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


