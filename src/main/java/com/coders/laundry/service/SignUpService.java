package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.dto.SignUpRequest;
import com.coders.laundry.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;

    public boolean nicknameDoubleCheck(String nickname){
        return memberRepository.selectByNickname(nickname)==null;
    }
    public String hashPassword (String password){
       return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean phoneNumDoubleCheck(String phoneNum){
        return memberRepository.selectByPhoneNum(phoneNum)==null;
    }

    public boolean signUp(SignUpRequest signUpRequest){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthday = LocalDate.parse(signUpRequest.getBirthday(), formatter);

        MemberEntity member = MemberEntity.builder()
                .phoneNum(signUpRequest.getPhoneNum())
                .password(hashPassword(signUpRequest.getPassword()))
                .nickname(signUpRequest.getNickname())
                .birthday(birthday)
                .gender(signUpRequest.getGender())
                .build();

        memberRepository.insert(member);

        return true;

    }
}
