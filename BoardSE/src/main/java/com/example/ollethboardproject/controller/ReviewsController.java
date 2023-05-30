package com.example.ollethboardproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewsController {

    //    @PreAuthorize("hasAnyRole('USER', 'VIP')")
//    @PostMapping("/reviews")
//    public ResponseEntity<String> reviews(Authentication authentication){
//        return ResponseEntity.ok().body(authentication.getName() + " 님의 권한 : " + authentication.getAuthorities());
//    }

    @GetMapping("/reviews")
    public void reviews(Authentication authentication){

    }
}
