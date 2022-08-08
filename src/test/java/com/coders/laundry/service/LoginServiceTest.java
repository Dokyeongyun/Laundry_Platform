package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.dto.LoginResponse;
import com.coders.laundry.dto.Member;
import com.coders.laundry.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    private MemberRepository memberRepository;
    private LoginService loginService;
    private TokenManagerService tokenManagerService;

    @BeforeEach
    public void setup() {
        memberRepository = mock(MemberRepository.class);
        tokenManagerService = mock(TokenManagerService.class);
        loginService = new LoginService(memberRepository, tokenManagerService);
    }

    @Test
    @DisplayName("로그인 로직 수행하는 메서드")
    void login() {

        //Arrange
        String phoneNum = "01012345678";
        String password = "testPassword";
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        MemberEntity member = MemberEntity.builder()
                .phoneNum(phoneNum)
                .password(hashPassword)
                .build();

        when(memberRepository.selectByPhoneNumber(phoneNum)).thenReturn(member);

        //Act
        LoginResponse loginResponse = loginService.login(phoneNum, password);

        //Assert
        Member memberInfo = loginResponse.getMember();
        assertEquals(memberInfo.getPhoneNum(), member.getPhoneNum());
        assertTrue(BCrypt.checkpw(password, hashPassword));

        //Arrange 입력받은 휴대폰 번호를 가진 회원이 DB에 존재하지 않는 경우
        when(memberRepository.selectByPhoneNumber(phoneNum)).thenReturn(null);

        //Act
        LoginResponse loginResponsePhoneNumMiss = loginService.login(phoneNum, password);

        //Assert
        assertNull(loginResponsePhoneNumMiss);

        //Arrange 입력받은 비밀번호가 DB와 일치하지않는 경우
        String passwordMiss = "testPasswordMiss";
        when(memberRepository.selectByPhoneNumber(phoneNum)).thenReturn(member);

        //Act
        LoginResponse loginResponsePasswordMiss = loginService.login(phoneNum, passwordMiss);

        //Assert
        assertNull(loginResponsePasswordMiss);
        assertFalse(BCrypt.checkpw(passwordMiss,hashPassword));

    }
}