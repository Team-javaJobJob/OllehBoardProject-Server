package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.member.MemberJoinRequest;
import com.example.ollethboardproject.controller.request.member.MemberLoginRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.Role;
import com.example.ollethboardproject.domain.dto.MemberDTO;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.repository.MemberRepository;
import com.example.ollethboardproject.service.MemberService;
import com.example.ollethboardproject.utils.TokenInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithAnonymousUser
    public void 회원가입() throws Exception {
        when(memberService.join(any(MemberJoinRequest.class))).thenReturn(mock(MemberDTO.class));

        mockMvc.perform(post("/api/v1/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberJoinRequest("userName", "password", "nick", Gender.MALE)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void 회원가입시_이미등록된_아이디가있다면_에러반환() throws Exception {
        when(memberService.join(any(MemberJoinRequest.class))).thenThrow(new OllehException(ErrorCode.DUPLICATED_USERNAME));

        mockMvc.perform(post("/api/v1/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberJoinRequest("userName", "password", "nick", Gender.MALE)))
                ).andDo(print())
                .andExpect(status().is(ErrorCode.DUPLICATED_USERNAME.getHttpStatus().value()));
    }

    @Test
    @WithAnonymousUser
    public void 로그인() throws Exception {
        when(memberService.login(any(MemberLoginRequest.class))).thenReturn(mock(TokenInfo.class));

        mockMvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberLoginRequest("userName", "password")))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 로그인시_유저아이디가_존재하지않는다면_에러반환() throws Exception {
        when(memberService.login(any(MemberLoginRequest.class))).thenThrow(new OllehException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberLoginRequest("userName", "password")))
                ).andDo(print())
                .andExpect(status().is(ErrorCode.USER_NOT_FOUND.getHttpStatus().value()));
    }

    @Test
    @WithAnonymousUser
    void 로그인시_패스워드가_다르다면_에러반환() throws Exception {
        when(memberService.login(any(MemberLoginRequest.class))).thenThrow(new OllehException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberLoginRequest("userName", "password")))
                ).andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PASSWORD.getHttpStatus().value()));
    }
}
