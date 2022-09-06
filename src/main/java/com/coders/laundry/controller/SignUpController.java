package com.coders.laundry.controller;

import com.coders.laundry.domain.exceptions.NicknameDuplicatedException;
import com.coders.laundry.domain.exceptions.PhoneNumberDuplicatedException;
import com.coders.laundry.dto.ErrorResponse;
import com.coders.laundry.dto.SignUpRequest;
import com.coders.laundry.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/api/member")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthday = LocalDate.parse(signUpRequest.getBirthday(), formatter);

        try {
            signUpService.signUp(signUpRequest, birthday);
        }catch (NicknameDuplicatedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("중복된 닉네임"));
        }catch (PhoneNumberDuplicatedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("중복된 휴대폰번호"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
