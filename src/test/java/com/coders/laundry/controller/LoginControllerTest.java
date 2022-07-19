package com.coders.laundry.controller;

import com.coders.laundry.dto.LoginRequest;
import com.coders.laundry.dto.LoginResponse;
import com.coders.laundry.repository.MemberRepository;
import com.coders.laundry.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private LoginService loginService;
    private LoginController loginController;

    @BeforeEach
    public void setup(){
        loginService = mock(LoginService.class);
        loginController = new LoginController(loginService);
    }

    @Test
    void login() {
        //Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .phoneNum("01012341235")
                .password("test12345")
                .build();

        when(loginService.existMember(loginRequest.getPhoneNum())).thenReturn(true);
        when(loginService.matchPW(loginRequest.getPhoneNum(),loginRequest.getPassword())).thenReturn(true);
        LoginResponse loginResponse = LoginResponse.builder().build();
        when(loginService.getLoginResponse(loginRequest.getPhoneNum())).thenReturn(loginResponse);
        //Act
        ResponseEntity result = loginController.login(loginRequest);

        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(loginResponse, result.getBody());

    }
}