package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.community.CommunityCreateRequest;
import com.example.ollethboardproject.controller.request.community.CommunityUpdateRequest;
import com.example.ollethboardproject.controller.response.CommunityMemberResponse;
import com.example.ollethboardproject.controller.response.Response;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.domain.dto.CommunityMemberDTO;
import com.example.ollethboardproject.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.stream.Collectors;

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

    // 해시태그 검색을 통한 커뮤니티 조회
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<CommunityDTO>> findCommunitiesByKeyword(@PathVariable String keyword) {
        log.info("GET /api/v1/communities/keyword/{}", keyword);
        List<CommunityDTO> communityDTOList = communityService.findCommunitiesByKeyword(keyword);
        return new ResponseEntity<>(communityDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommunityDTO> createCommunity(
            @RequestPart(value = "communityCreateRequest", required = false) CommunityCreateRequest communityCreateRequest,
            @RequestPart(value = "file", required = false) MultipartFile file, Authentication authentication) throws Exception {
        log.info("POST /api/v1/communities - communityCreateRequest & authentication : {}, {}, {}", communityCreateRequest, file, authentication);
        CommunityDTO createdCommunityDTO = communityService.createCommunity(communityCreateRequest, file, authentication);
        return new ResponseEntity<>(createdCommunityDTO, HttpStatus.CREATED);
    }

    // 커뮤니티 정보 수정
    @PutMapping("/{communityId}")
    public ResponseEntity<CommunityDTO> updateCommunity(@PathVariable Long communityId, @RequestPart CommunityUpdateRequest communityUpdateRequest, @RequestPart MultipartFile file, Authentication authentication) throws Exception {
        log.info("PUT /api/v1/communities/{}", communityId);
        CommunityDTO updatedCommunityDTO = communityService.updateCommunity(communityId, communityUpdateRequest, file, authentication);
        return new ResponseEntity<>(updatedCommunityDTO, HttpStatus.OK);
    }

    // 커뮤니티 삭제
    @DeleteMapping("/{communityId}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long communityId, Authentication authentication) {
        log.info("DELETE /api/v1/communities/{}", communityId);
        communityService.deleteCommunity(communityId, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 커뮤니티 가입
    @PostMapping("/{communityId}")
    public Response<Void> joinCommunity(@PathVariable Long communityId, Authentication authentication) {
        log.info("POST /api/v1/communities/{}", communityId);
        communityService.joinCommunity(communityId, authentication);
        return Response.success();
    }

    // 가입된 멤버만 커뮤니티 멤버 조회 가능
    @GetMapping("{communityId}")
    public Response<List<CommunityMemberResponse>> selectCommunity(@PathVariable Long communityId, Authentication authentication, Pageable pageable) {
        log.info("GET /api/v1/communities/{}", communityId);
        List<CommunityMemberDTO> communityMemberDTOList = communityService.selectCommunity(communityId, authentication, pageable);
        List<CommunityMemberResponse> CommunityMemberResponses = communityMemberDTOList.stream().map(CommunityMemberResponse::fromCommunityMemberDTO).collect(Collectors.toList());
        return Response.success(CommunityMemberResponses);
    }

    // 좋아요(=올래)
    @PostMapping("/{communityId}/olleh")
    public Response<Void> olleh(@PathVariable Long communityId, Authentication authentication) {
        communityService.addOlleh(authentication.getName(), communityId);
        return Response.success();
    }

    // 좋아요 수
    @GetMapping("/{communityId}/olleh")
    public Response<Integer> olleh(@PathVariable Long communityId) {
        Integer ollehCount = communityService.ollehCount(communityId);
        return Response.success(ollehCount);
    }

    // 최신순 정렬
    @GetMapping("/latest")
    public Response<List<CommunityDTO>> getLatestCommunity() {
        List<CommunityDTO> latestCommunities = communityService.getLatestCommunity();
        return Response.success(latestCommunities);
    }

}