package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.member.FindByPwRequest;
import com.example.ollethboardproject.controller.request.member.MemberJoinRequest;
import com.example.ollethboardproject.controller.request.member.MemberLoginRequest;
import com.example.ollethboardproject.controller.request.member.MemberUpdateRequest;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    @WithMockUser
    void 비밀번호가_일치하면_회원정보_조회() throws Exception {
        // 반환 객체 세팅
        MemberDTO memberDTO = new MemberDTO(1L, "userName", "password", "nickName", Gender.FEMALE);

        when(memberService.findMemberByPassword(anyString(), any())).thenReturn(memberDTO);

        mockMvc.perform(post("/api/v1/members/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new FindByPwRequest("password")))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userName").value("userName"))
                .andExpect(jsonPath("$.nickName").value("nickName"))
                .andExpect(jsonPath("$.gender").value("FEMALE"));
    }

    @Test
    @WithMockUser
    void 비밀번호가_일치하면_회원정보_수정() throws Exception {
        // 반환 객체 세팅
        MemberDTO memberDTO = new MemberDTO(1L, "userName", "password", "nickName", Gender.FEMALE);

        when(memberService.updateMember(any(), any())).thenReturn(memberDTO);

        mockMvc.perform(put("/api/v1/members/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new FindByPwRequest("password")))
                        .content(objectMapper.writeValueAsBytes(new MemberUpdateRequest("requestPw", "userName1", "password1", "nickName1", Gender.MALE)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 비밀번호가_일치하면_계정_삭제() throws Exception {

        doNothing().when(memberService).deleteMember(anyString(), any());

        mockMvc.perform(delete("/api/v1/members/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new FindByPwRequest("password")))
                ).andDo(print())
                .andExpect(status().isNoContent());
    }
}
