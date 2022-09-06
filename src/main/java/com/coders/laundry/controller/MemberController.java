package com.coders.laundry.controller;

import com.coders.laundry.dto.LaundryLike;
import com.coders.laundry.service.MemberService;
import com.coders.laundry.service.TokenManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final TokenManagerService tokenManagerService;
    private final MemberService memberService;

    @GetMapping("/{memberId}/laundries/likes")
    public ResponseEntity<?> findMyLikes(@RequestHeader String token, @PathVariable(value = "memberId") int memberId){
        if(token == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청. 토큰미전송");
        }
        if(!tokenManagerService.verify(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증실패. 유효하지않은 토큰입니다.");
        }

        int id = tokenManagerService.findMemberId(token);
        ArrayList<LaundryLike> myLikes = memberService.findMyLikes(id);

        return ResponseEntity.status(HttpStatus.OK).body(myLikes);

    }
}
