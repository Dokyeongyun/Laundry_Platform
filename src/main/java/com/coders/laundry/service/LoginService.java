package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginService {
    private MemberRepository memberRepository;
    @Autowired
    LoginService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    //회원존재여부확인
    public boolean existMember(String phoneNum){
        MemberEntity login_member = memberRepository.selectByPhoneNumber(phoneNum);
        if (login_member == null)
            return false;
        else
            return true;
    }

}
