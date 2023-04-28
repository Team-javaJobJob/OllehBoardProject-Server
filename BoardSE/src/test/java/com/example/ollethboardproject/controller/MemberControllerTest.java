//package com.example.ollethboardproject.controller;
//
//import com.example.ollethboardproject.controller.request.MemberJoinRequest;
//import com.example.ollethboardproject.controller.request.MemberLoginRequest;
//import com.example.ollethboardproject.domain.Gender;
//import com.example.ollethboardproject.domain.dto.MemberDTO;
//import com.example.ollethboardproject.domain.entity.Member;
//import com.example.ollethboardproject.exception.BoardException;
//import com.example.ollethboardproject.exception.ErrorCode;
//import com.example.ollethboardproject.service.MemberService;
//import com.example.ollethboardproject.utils.TokenInfo;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//class MemberControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private MemberService memberService;
//
//    @Test
//    @DisplayName("회원가입 성공")
//    void join_success() throws Exception {
//
//        when(memberService.join(any())).thenReturn(mock(MemberDTO.class));
//
//        mockMvc.perform(post("/api/v1/members/join")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new MemberJoinRequest("userName", "password", "nickName", Gender.MALE))))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    @DisplayName("회원아이디 중복 실패")
//    void join_fail_duplicated() throws Exception {
//        when(memberService.join(new MemberJoinRequest("userName", "password", "nickName", Gender.MALE)))
//                .thenThrow(new BoardException(ErrorCode.DUPLICATED_USERNAME));
//
//        mockMvc.perform(post("/api/v1/members/join")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new MemberJoinRequest("userName", "password", "nickName", Gender.MALE))))
//                .andDo(print())
//                .andExpect(status().isConflict());
//        verify(memberService).join(any());
//    }
//
//    @Test
//    void 로그인_정상작동() throws Exception {
//        when(memberService.login(any())).thenReturn(mock(TokenInfo.class));
//
//        mockMvc.perform(post("/api/v1/members/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new MemberLoginRequest("userName", "password"))))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//
//    //TODO: 테스트 코드 작성
//
//}