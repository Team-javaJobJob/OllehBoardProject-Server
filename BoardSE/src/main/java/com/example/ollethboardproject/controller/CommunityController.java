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
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

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

    //해시태그 검색을 통한 커뮤니티 조회
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<CommunityDTO>> findCommunitiesByKeyword(@PathVariable String keyword) {
        log.info("GET /api/v1/communities/keyword/{}", keyword);
        List<CommunityDTO> communityDTOList = communityService.findCommunitiesByKeyword(keyword);
        return new ResponseEntity<>(communityDTOList, HttpStatus.OK);
    }

    //커뮤니티 생성
//    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<CommunityDTO> createCommunity(
//            @RequestPart CommunityCreateRequest communityCreateRequest,
//            @RequestPart MultipartFile file, Authentication authentication) throws Exception {
//        log.info("POST /api/v1/communities - communityCreateRequest & authentication : {}, {}, {}", communityCreateRequest, file, authentication);
//        CommunityDTO createdCommunityDTO = communityService.createCommunity(communityCreateRequest, file, authentication);
//        return new ResponseEntity<>(createdCommunityDTO, HttpStatus.CREATED);
//    }


    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommunityDTO> createCommunity(
            @RequestPart(value = "communityCreateRequest", required = false) CommunityCreateRequest communityCreateRequest,
            @RequestPart(value = "file", required = false) MultipartFile file, Authentication authentication) throws Exception {
        log.info("POST /api/v1/communities - communityCreateRequest & authentication : {}, {}, {}", communityCreateRequest, file, authentication);
        CommunityDTO createdCommunityDTO = communityService.createCommunity(communityCreateRequest, file, authentication);
        return new ResponseEntity<>(createdCommunityDTO, HttpStatus.CREATED);
    }

    // 파일 업로드 처리
//    @Bean
//    public MultipartResolver multipartResolver() {
//        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(10000000);
//        return multipartResolver;
//    }

//    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> createCommunity(
//            @RequestPart CommunityCreateRequest communityCreateRequest,
//            @RequestPart(value = "file") MultipartFile file, Authentication authentication) throws Exception {
//        log.info("POST /api/v1/communities - communityCreateRequest & authentication : {}, {}, {}", communityCreateRequest, file, authentication);
//        CommunityDTO createdCommunityDTO = communityService.createCommunity(communityCreateRequest, file, authentication);
//        return new ResponseEntity<>(createdCommunityDTO, HttpStatus.CREATED);
//    }


    //커뮤니티 정보 수정
    //TODO: LIST -> id 외 조회 기준 추가
    @PutMapping("{id}")
    public ResponseEntity<CommunityDTO> updateCommunity(@PathVariable Long id, @RequestPart CommunityUpdateRequest communityUpdateRequest, @RequestPart MultipartFile file, Authentication authentication) throws Exception {
        log.info("PUT /api/v1/communities/{}", id);
        CommunityDTO updatedCommunityDTO = communityService.updateCommunity(id, communityUpdateRequest, file, authentication);
        return new ResponseEntity<>(updatedCommunityDTO, HttpStatus.OK);
    }

    //커뮤니티 삭제
    //TODO: LIST -> id 외 조회 기준 추가
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id, Authentication authentication) {
        log.info("DELETE /api/v1/communities/{}", id);
        communityService.deleteCommunity(id, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //커뮤니티 가입
    @PostMapping("{communityId}")
    public Response<Void> joinCommunity(@PathVariable Long communityId, Authentication authentication) {
        log.info("POST /api/v1/communities/{}", communityId);
        communityService.joinCommunity(communityId, authentication);
        return Response.success();
    }

    //가입된 멤버만 멤버들을 조회할 수 있다.
    @GetMapping("{communityId}")
    public Response<List<CommunityMemberResponse>> selectCommunity(@PathVariable Long communityId, Authentication authentication, Pageable pageable) {
        log.info("GET /api/v1/communities/{}", communityId);
        List<CommunityMemberDTO> communityMemberDTOList = communityService.selectCommunity(communityId, authentication,pageable);
        List<CommunityMemberResponse> CommunityMemberResponses = communityMemberDTOList.stream().map(CommunityMemberResponse::fromCommunityMemberDTO).collect(Collectors.toList());
        return Response.success(CommunityMemberResponses);
    }


    //관심사&키워드 기반 커뮤니티 추천 기능


    //좋아요(=올래)
    @PostMapping("/{communityId}/olleh")
    public Response<Void> olleh(@PathVariable Long communityId, Authentication authentication){
        communityService.addOlleh(authentication.getName(), communityId);
        return Response.success();
    }

    //좋아요수      //TODO: GET Mapping 삭제하고 , 좋아요 수를 커뮤니티에 노출 시키는 방향으로
    @GetMapping("/{communityId}/olleh")
    public Response<Integer> olleh(@PathVariable Long communityId){
        Integer ollehCount = communityService.ollehCount(communityId); //communityService 의 ollehCount 메소드를 호출 communityId에 해당하는 community 객체의 Olleh 개수 가져옴
        return Response.success(ollehCount); //ollehCount 값을 Response 객체에 담아서 반환
    }

    //최신순 정렬
    @GetMapping("/latest")
    public Response<List<CommunityDTO>> getLatestCommunity() { //List<Community> 타입의 Response 반환
        List<CommunityDTO> latestCommunities = communityService.getLatestCommunity();
        return Response.success(latestCommunities);
    }

    //추천순 (올레순) 정렬
    @GetMapping("/topOlleh")
    public Response<List<CommunityDTO>> getTopOllehCommunity() {
        List<CommunityDTO> topOllehCommunities = communityService.getTopOllehCommunity();
        return Response.success(topOllehCommunities);
    }
}