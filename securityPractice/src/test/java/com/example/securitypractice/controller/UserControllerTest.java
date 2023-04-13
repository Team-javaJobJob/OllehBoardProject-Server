package com.example.securitypractice.controller;

import com.example.securitypractice.domain.entity.UserEntity;
import com.example.securitypractice.exception.AppException;
import com.example.securitypractice.exception.ErrorCode;
import com.example.securitypractice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;  //자바의 오브젝트를 제이슨으로 변환 (맵핑) 시키는 역할

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void join() throws Exception {

        String userName = "userName";
        String password = "password";

        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                       )
                .andDo(print())
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("회원가입 중복")
//    @WithMockUser
//    void join_fail() throws Exception {
//
//        String userName = "userName";
//        String password = "password";
//
//        when(userService.join(any(), any())).thenThrow(new RuntimeException());
//
//
//        mockMvc.perform(post("/api/v1/users/join")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password))))
//                .andDo(print())
//                .andExpect(status().isConflict());
//    }
//
//    @Test
//    @DisplayName("로그인 성공")
//    @WithMockUser
//    void login_success() throws Exception {
//
//        String userName = "userName";
//        String password = "password";
//
//        when(userService.login(userName, password)).thenReturn("token");
//
//        mockMvc.perform(post("/api/v1/users/login")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password))))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("로그인시 존재하지않는 아이디")
//    @WithMockUser
//    void login_fail1() throws Exception {
//
//        String userName = "userName";
//        String password = "password";
//
//        when(userService.login(any(), any())).thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));
//
//        mockMvc.perform(post("/api/v1/users/login")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password))))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("로그인시 존재하지않는 패스워드")
//    @WithMockUser
//    void login_fail2() throws Exception {
//
//        String userName = "userName";
//        String password = "password";
//
//        when(userService.login(any(), any())).thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, ""));
//
//        mockMvc.perform(post("/api/v1/users/login")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password))))
//                .andDo(print())
//                .andExpect(status().isUnauthorized());
//    }

}