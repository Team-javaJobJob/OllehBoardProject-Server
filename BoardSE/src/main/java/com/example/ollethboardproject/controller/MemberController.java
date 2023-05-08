package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.MemberJoinRequest;
import com.example.ollethboardproject.controller.request.MemberLoginRequest;
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
}
