package com.example.ollethboardproject.Service;

import com.example.ollethboardproject.controller.request.member.MemberJoinRequest;
import com.example.ollethboardproject.controller.request.member.MemberLoginRequest;
import com.example.ollethboardproject.controller.request.member.MemberUpdateRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.Role;
import com.example.ollethboardproject.domain.dto.MemberDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    @Test
    void 비밀번호_일치시_회원정보_조회() {
        // given
        // 검증할 비밀번호
        String password = "password";
        // 검증 대상 - 회원 정보 객체
        Member member = Member.of(new MemberJoinRequest("userName", "password", "nickName", Gender.MALE));

        // when
        // 비밀번호 일치 여부 검증
        when(encoder.matches(password, encoder.encode(member.getPassword())))
                .thenReturn(true);
        // 조회한 회원 정보 MemberDTO 타입으로 반환
        when(memberService.findMemberByPassword(any(String.class), any(Authentication.class)))
                .thenReturn(MemberDTO.fromEntity(member));
        // then
        Assertions.assertDoesNotThrow(() ->
                memberService.findMemberByPassword(any(String.class), any(Authentication.class)));
    }

    @Test
    void 비밀번호_일치시_회원정보_수정() {
        // given
        // 검증할 비밀번호
        String password = "password";
        // 검증 대상 - 회원 정보 객체
        Member member = Member.of(new MemberJoinRequest("userName", "password", "nickName", Gender.MALE));
        // 수정할 회원 정보 객체
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest("requestPW", "userName1", "password1", "nickName1", Gender.FEMALE);
        Member updatedMember = Member.toPw(memberUpdateRequest);

        // when
        // 수정할 이름 중복 여부 검증
        when(memberRepository.findByUserName(memberUpdateRequest.getUserName()))
                .thenThrow(new OllehException(ErrorCode.DUPLICATED_USERNAME));
        // 비밀번호 일치 여부 검증
        when(encoder.matches(password, encoder.encode(member.getPassword())))
                .thenReturn(true);
        // 수정된 회원 정보 MemberDTO 타입으로 반환
        when(memberService.updateMember(any(), any()))
                .thenReturn(MemberDTO.fromEntity(updatedMember));

        // then
        Assertions.assertDoesNotThrow(() ->
                memberService.updateMember(any(), any()));
    }

    @Test
    public void 비밀번호_일치시_회원정보_삭제() {
        // given
        // 검증할 비밀번호
        String password = "password";
        // 검증 대상 - 회원 정보 객체
        Member member = Member.of(new MemberJoinRequest("userName", "password", "nickName", Gender.MALE));

        // when
        // 비밀번호 일치 여부 검증
        when(encoder.matches(password, encoder.encode(member.getPassword())))
                .thenReturn(true);
        // ID로 회원 정보 삭제
        doNothing().when(memberRepository).deleteById(member.getId());

        // then
        Assertions.assertDoesNotThrow(() ->
                memberService.deleteMember(any(String.class), any(Authentication.class)));
    }


}

