package com.coders.laundry.controller;

import com.coders.laundry.dto.ErrorResponse;
import com.coders.laundry.dto.LoginRequest;
import com.coders.laundry.dto.LoginResponse;
import com.coders.laundry.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginInfo){

        String phoneNum = loginInfo.getPhoneNum();
        String password = loginInfo.getPassword();

        LoginResponse loginResponse = loginService.login(phoneNum, password);

        if (loginResponse==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청정보"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

    }
}
