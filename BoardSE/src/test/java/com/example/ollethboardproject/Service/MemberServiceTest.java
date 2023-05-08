package com.example.ollethboardproject.Service;

import com.example.ollethboardproject.controller.request.member.MemberJoinRequest;
import com.example.ollethboardproject.controller.request.member.MemberLoginRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.Role;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.repository.MemberRepository;
import com.example.ollethboardproject.service.MemberService;
import com.example.ollethboardproject.utils.JwtTokenUtil;
import com.example.ollethboardproject.utils.TokenInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder encoder;


    @Test
    void 회원가입() {
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(
                "userName", "password", "nickName", Gender.MALE);

        when(memberRepository.findByUserName(memberJoinRequest.getUserName()))
                .thenReturn(Optional.empty());
        when(encoder.encode(memberJoinRequest.getPassword()))
                .thenReturn("encoded_password");
        when(memberRepository.save(any(Member.class)))
                .thenReturn(Member.of(memberJoinRequest));

        Assertions.assertDoesNotThrow(() -> memberService.join(memberJoinRequest));
    }

    @Test
    void 회원가입시_회원아이디가_이미존재할경우_에러반환() {
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(
                "userName", "password", "nickName", Gender.MALE);

        when(memberRepository.findByUserName(memberJoinRequest.getUserName()))
                .thenReturn(Optional.of(Member.of(memberJoinRequest)));

        OllehException ollehException = assertThrows(OllehException.class, ()
                -> memberService.join(memberJoinRequest));

        Assertions.assertEquals(ErrorCode.DUPLICATED_USERNAME, ollehException.getErrorCode());
    }

//    @Test
//    void 로그인() {
//        MemberLoginRequest memberLoginRequest = new MemberLoginRequest("userName", "password");
//        Member member = Member.of(new MemberJoinRequest("userName", "password", "nickName", Gender.MALE));
////        Member member = mock(Member.class);
//
//        when(memberRepository.findByUserName(memberLoginRequest.getUserName()))
//                .thenReturn(Optional.of(member));
//        when(encoder.matches(memberLoginRequest.getPassword(), encoder.encode(member.getPassword()))).thenReturn(true);
//
//        Assertions.assertDoesNotThrow(() -> memberService.login(memberLoginRequest));
//    }


}

