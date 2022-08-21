package com.coders.laundry.controller;

import com.coders.laundry.dto.ErrorResponse;
import com.coders.laundry.dto.SignUpRequest;
import com.coders.laundry.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/api/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){

        if(!signUpService.phoneNumDoubleCheck(signUpRequest.getPhoneNum()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("이미 존재하는 휴대폰번호"));

        if(!signUpService.nicknameDoubleCheck(signUpRequest.getNickname()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("이미 존재하는 닉네임"));signUpService.signUp(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }

}
