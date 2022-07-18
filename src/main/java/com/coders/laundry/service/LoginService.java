package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.repository.MemberRepository;
import org.mindrot.jbcrypt.BCrypt;
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
    //비밀번호 일치여부 확인
    public boolean matchPW(String phoneNum, String pw){
        MemberEntity login_member = memberRepository.selectByPhoneNumber(phoneNum);
        //사용자가 입력한 비밀번호와 DB에 저장된 비밀번호 비교
        return BCrypt.checkpw(pw, login_member.getPassword());
    }


}
