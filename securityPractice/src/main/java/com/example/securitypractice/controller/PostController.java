package com.example.securitypractice.controller;

import com.example.securitypractice.controller.request.CommentRequest;
import com.example.securitypractice.controller.request.PostCreateRequest;
import com.example.securitypractice.controller.request.PostModifyRequest;
import com.example.securitypractice.controller.response.CommentResponse;
import com.example.securitypractice.controller.response.PostResponse;
import com.example.securitypractice.controller.response.Response;
import com.example.securitypractice.domain.dto.Comment;
import com.example.securitypractice.domain.dto.Post;
import com.example.securitypractice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Long postId, @RequestBody PostModifyRequest request, Authentication authentication) {
        Post post = postService.modify(request.getTitle(), request.getBody(), postId, authentication.getName());
        PostResponse postModifyResponse = PostResponse.fromPost(post);
        return Response.success(postModifyResponse);
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(postId, authentication.getName());
        return Response.success();
    }

    @GetMapping
    public Response<Page<PostResponse>> list(Pageable pageable) {
        return Response.success(postService.list(pageable).map(PostResponse::fromPost));
    }

    @GetMapping("/my")
    public Response<Page<PostResponse>> my(Authentication authentication, Pageable pageable) {
        return Response.success(postService.my(authentication.getName(), pageable).map(PostResponse::fromPost));
    }

    @PostMapping("/{postId}/likes")
    public Response<Void> like(@PathVariable Long postId, Authentication authentication) {
        postService.like(postId, authentication.getName());
        return Response.success();
    }

    @GetMapping("/{postId}/likes")
    public Response<Integer> like(@PathVariable Long postId) {
        Integer likeCount = postService.likeCount(postId);
        return Response.success(likeCount);
    }

    @PostMapping("/{postId}/comments")
    public Response<Void> createComment(@PathVariable Long postId, Authentication authentication, @RequestBody CommentRequest commentRequest) {
        postService.createComment(postId, authentication.getName(), commentRequest.getComment());
        return Response.success();
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public Response<CommentResponse> modifyComment(@PathVariable Long postId, @PathVariable Long commentId, Authentication authentication, @RequestBody CommentRequest commentRequest) {
        Comment comment = postService.modifyComment(postId, commentId, authentication.getName(), commentRequest.getComment());
        return Response.success(CommentResponse.fromComment(comment));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public Response<Void> deleteComments(@PathVariable Long postId,@PathVariable Long commentId, Authentication authentication) {
        postService.deleteComment(postId,commentId,authentication.getName());
        return Response.success();
    }

}
