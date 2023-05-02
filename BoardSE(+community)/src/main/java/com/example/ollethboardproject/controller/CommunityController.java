package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.*;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.service.CommunityService;
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
@RequestMapping("/api/v1/communities")
public class CommunityController {
    private final CommunityService communityService;

    // 커뮤니티 전체 조회
    @GetMapping("")
    public ResponseEntity<List<CommunityDTO>> findAllCommunity() {
        log.info("GET /api/v1/communities");
        List<CommunityDTO> communityDTOList = communityService.findAllCommunities();
        return new ResponseEntity<>(communityDTOList, HttpStatus.OK);
    }

    //커뮤니티 생성
    @PostMapping("")
    public ResponseEntity<CommunityDTO> createCommunity(@RequestBody CommunityCreateRequest communityCreateRequest, Authentication authentication) {
        log.info("POST /api/v1/communities");
        log.info("CommunityCreateRequest & authentication: {}, {} ", communityCreateRequest, authentication);
        CommunityDTO createdCommunityDTO = communityService.createCommunity(communityCreateRequest, authentication);
        log.info("createdCommunityDTO : {}", createdCommunityDTO);
        return new ResponseEntity<>(createdCommunityDTO, HttpStatus.CREATED);
    }

    //커뮤니티 정보 수정
    //TODO: LIST -> id 외 조회 기준 추가
    @PutMapping("/{id}")
    public ResponseEntity<CommunityDTO> updateCommunity(@PathVariable Long id, @RequestBody CommunityUpdateRequest communityUpdateRequest , Authentication authentication) {
        log.info("PUT /api/v1/communities/{}", id);
        CommunityDTO updatedCommunityDTO = communityService.updateCommunity(id, communityUpdateRequest, authentication);
        return new ResponseEntity<>(updatedCommunityDTO, HttpStatus.OK);
    }

    //커뮤니티 삭제
    //TODO: LIST -> id 외 조회 기준 추가
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        log.info("DELETE /api/v1/communities/{}", id);
        communityService.deleteBoard(id, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //커뮤니티 가입
    //TODO: LIST -> 관리자 승인 및 취소 기능 추가

    //커뮤니티 기가입 멤버 조회

    //관심사&키워드 기반 커뮤니티 추천 기능
    //TODO: ENUM 사용


}
