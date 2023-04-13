package com.example.securitypractice.controller;

import com.example.securitypractice.controller.request.UserJoinRequest;
import com.example.securitypractice.controller.request.UserLoginRequest;
import com.example.securitypractice.controller.response.Response;
import com.example.securitypractice.controller.response.UserJoinResponse;
import com.example.securitypractice.controller.response.UserLoginResponse;
import com.example.securitypractice.domain.dto.User;
import com.example.securitypractice.service.UserService;
import com.example.securitypractice.utils.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        User user = userService.join(userJoinRequest.getUserName(), userJoinRequest.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        TokenInfo tokens = userService.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(tokens.getAccessToken(), tokens.getRefreshToken()));
    }
}
