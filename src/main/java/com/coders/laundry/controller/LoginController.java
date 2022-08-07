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
    public ResponseEntity login(@RequestBody LoginRequest loginInfo){

        String phoneNum = loginInfo.getPhoneNum();
        String password = loginInfo.getPassword();

        if (!loginService.existMember(phoneNum)) {
            return new ResponseEntity<>("존재하지않는 ID입니다.", HttpStatus.BAD_REQUEST);
        }

        if (!loginService.matchPW(phoneNum, password)) {
            return new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
        }
        LoginResponse loginResponse = loginService.getLoginResponse(phoneNum);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
