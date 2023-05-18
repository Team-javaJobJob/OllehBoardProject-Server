
package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.member.MemberJoinRequest;
import com.example.ollethboardproject.controller.request.member.MemberLoginRequest;

import com.example.ollethboardproject.controller.response.MemberJoinResponse;
import com.example.ollethboardproject.controller.response.MemberLoginResponse;
import com.example.ollethboardproject.controller.response.Response;
import com.example.ollethboardproject.domain.dto.MemberDTO;
import com.example.ollethboardproject.service.MemberService;
import com.example.ollethboardproject.utils.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")

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
<<<<<<< HEAD
=======

    //회원 정보 조회
    @PostMapping("/myPage")
    public ResponseEntity<MemberDTO> findMemberByPw(@RequestBody String requestPw, Authentication authentication) {
        MemberDTO memberDTO = memberService.findMemberByPassword(requestPw, authentication);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    //회원 정보 수정
    @PutMapping("/myPage/update")
    public ResponseEntity<MemberDTO> updateMember(@RequestBody MemberUpdateRequest memberUpdateRequest , Authentication authentication) {
        MemberDTO updatedMemberDTO = memberService.updateMember(memberUpdateRequest, authentication);
        return new ResponseEntity<>(updatedMemberDTO, HttpStatus.OK);
    }

    //회원 정보 삭제
    @PostMapping("/myPage/delete")
    public ResponseEntity<Void> deleteMember(@RequestBody String requestPw, Authentication authentication) {
        memberService.deleteMember(requestPw, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
>>>>>>> main
}

