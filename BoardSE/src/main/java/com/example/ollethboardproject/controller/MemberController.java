
package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.member.MemberJoinRequest;
import com.example.ollethboardproject.controller.request.member.MemberLoginRequest;

import com.example.ollethboardproject.controller.request.member.MemberUpdateRequest;
import com.example.ollethboardproject.controller.response.MemberJoinResponse;
import com.example.ollethboardproject.controller.response.MemberLoginResponse;
import com.example.ollethboardproject.controller.response.Response;
import com.example.ollethboardproject.controller.response.*;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.domain.dto.MemberDTO;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.service.MemberService;
import com.example.ollethboardproject.utils.ClassUtil;
import com.example.ollethboardproject.utils.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@CrossOrigin(origins = "/api/v1/post")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입

    @PostMapping("/join")
    @CrossOrigin(origins = "http://localhost:3000")
    public Response<MemberJoinResponse> join(@RequestBody MemberJoinRequest memberJoinRequest) {
        MemberDTO memberDTO = memberService.join(memberJoinRequest);
        return Response.success(MemberJoinResponse.fromUserDTO(memberDTO));
    }



    //로그인
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public Response<MemberLoginResponse> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        TokenInfo tokens = memberService.login(memberLoginRequest);
        return Response.success(new MemberLoginResponse(tokens.getAccessToken(), tokens.getRefreshToken()));
    }


    //회원 정보 조회
    @PostMapping("/myPage")
    public ResponseEntity<MemberDTO> findMemberByPw(@RequestBody String requestPw, Authentication authentication) {
        MemberDTO memberDTO = memberService.findMemberByPassword(requestPw, authentication);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);

    }

    //회원 정보 수정
    @PutMapping("/myPage/update")
    public ResponseEntity<MemberDTO> updateMember(@RequestBody MemberUpdateRequest memberUpdateRequest, Authentication authentication) {
        MemberDTO updatedMemberDTO = memberService.updateMember(memberUpdateRequest, authentication);
        return new ResponseEntity<>(updatedMemberDTO, HttpStatus.OK);
    }

    //회원 정보 삭제
    @PostMapping("/myPage/delete")
    public ResponseEntity<Void> deleteMember(@RequestBody String requestPw, Authentication authentication) {
        memberService.deleteMember(requestPw, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/myLog/olleh")   // 올레로그: 좋아요를 누른 커뮤니티의 이름과 ID를 조회한다
    public ResponseEntity<List<OllehLogResponse>> selectOllehLog(Authentication authentication) {
        List<OllehLogResponse> ollehLogResponses = memberService.selectOllehLog(authentication)
                .stream()
                .map(OllehLogResponse::fromDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ollehLogResponses, HttpStatus.OK);
    }


    @GetMapping("/myLog/post")  //내가 작성한 게시물을 조회한다.
    public ResponseEntity<List<PostLogResponse>> selectPostLog(Authentication authentication) {
        List<PostLogResponse> postLogResponses = memberService.selectPostLog(authentication)
                .stream()
                .map(PostLogResponse::fromDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postLogResponses, HttpStatus.OK);
    }

    @GetMapping("/myLog/community") //내가 참여중인 커뮤니티(채팅방)를 조회 한다.
    public ResponseEntity<List<CommunityLogResponse>> selectCommunityLog(Authentication authentication) {
        List<CommunityLogResponse> communityLogResponses = memberService.selectCommunityLog(authentication)
                .stream()
                .map(CommunityLogResponse::fromDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(communityLogResponses, HttpStatus.OK);
    }

    //코드추가(챗용)
    @GetMapping("/myInfo")
    @ResponseBody
    public ResponseEntity<Member> getMyInfo(Authentication authentication) {
        Member member = ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

}

