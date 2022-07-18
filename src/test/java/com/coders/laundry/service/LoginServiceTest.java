package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    private MemberRepository memberRepository;
    private LoginService loginService;

    @BeforeEach
    public void setup(){
        memberRepository = mock(MemberRepository.class);
        loginService = new LoginService(memberRepository);
    }

    @Test
    @DisplayName("DB에 해당 휴대폰번호를 가진 회원이 있는지 검색해서 존재여부를 반환하는 메서드")
    void existMember() {
        //Arrange
        //사용자가 입력한 휴대폰 번호값
        String phoneNum= "01012345678";
        //회원 객체 하나 생성
        MemberEntity member = MemberEntity.builder().build();
        //해당 휴대폰번호를 가진 회원이 있는지 검색했을 때 null이 아닌 회원객체가 나온다고 가정
        when(memberRepository.selectByPhoneNumber(phoneNum)).thenReturn(member);

        //Act
        //해당 휴대폰번호를 가진 회원의 존재여부확인
        boolean isMember = loginService.existMember(phoneNum);

        //Assert
        //존재여부 true인지 검증
        assertTrue(isMember);

    }

    @Test
    @DisplayName("DB에 있는 비밀번호와 사용자가 입력한 비밀번호의 일치여부를 확인하는 메서드")
    void matchPW(){
        //Arrange
        //사용자가 입력한 휴대폰번호와 비밀번호 값
        String phoneNum = "01012341234";
        String pw = "test1234";
        //DB에 암호화돼 저장된 비밀번호 값
        String hashPW = BCrypt.hashpw(pw, BCrypt.gensalt());
        MemberEntity member = MemberEntity.builder()
                .password(hashPW)
                .build();
        //휴대폰 번호로 검색했을 때 위에서 생성한 회원객체 member가 나온다고 가정
        when(memberRepository.selectByPhoneNumber(phoneNum)).thenReturn(member);

        //Act
        //해당 휴대폰 번호를 가진 회원의 비밀번호가 사용자가 입력한 비밀번호와 맞는지 확인
        boolean isMatch = loginService.matchPW(phoneNum, pw);

        //Assert
        //일치여부가 true인지 검증
        assertTrue(isMatch);
    }
}