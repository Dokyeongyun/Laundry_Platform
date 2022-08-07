package com.coders.laundry.controller;

import com.coders.laundry.dto.LoginRequest;
import com.coders.laundry.dto.LoginResponse;
import com.coders.laundry.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginInfo){

        String phoneNum = loginInfo.getPhoneNum();
        String password = loginInfo.getPassword();

        LoginResponse loginResponse = loginService.login(phoneNum, password);

        if (loginResponse==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);

    }
}
