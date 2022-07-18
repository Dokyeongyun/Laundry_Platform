package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
}