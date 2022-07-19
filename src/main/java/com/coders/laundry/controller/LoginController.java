package com.coders.laundry.controller;

import com.coders.laundry.dto.LoginRequest;
import com.coders.laundry.dto.LoginResponse;
import com.coders.laundry.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class LoginController {
    private LoginService loginService;
    @Autowired
    LoginController(LoginService loginService){
        this.loginService = loginService;
    }
    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody LoginRequest loginInfo){
        String phoneNum = loginInfo.getPhoneNum();
        String pw = loginInfo.getPassword();
        //DB에 있는지 확인.
        if (!loginService.existMember(phoneNum))
            return new ResponseEntity<>("존재하지않는 ID입니다.", HttpStatus.BAD_REQUEST);
        //비밀번호 맞는지 확인.
        if (!loginService.matchPW(phoneNum, pw))
            return new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
        LoginResponse loginResponse = loginService.getLoginResponse(phoneNum);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
