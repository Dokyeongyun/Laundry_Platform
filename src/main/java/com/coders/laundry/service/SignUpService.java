package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.domain.exceptions.NicknameDuplicatedException;
import com.coders.laundry.domain.exceptions.PhoneNumberDuplicatedException;
import com.coders.laundry.dto.SignUpRequest;
import com.coders.laundry.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;

    public boolean signUp(SignUpRequest signUpRequest, LocalDate birthday){

        MemberEntity entity = memberRepository.selectByNickname(signUpRequest.getNickname());
        if (entity != null){
            throw new NicknameDuplicatedException();
        }

        MemberEntity entityByPhoneNumber = memberRepository.selectByPhoneNum(signUpRequest.getPhoneNum());
        if(entityByPhoneNumber != null){
            throw new PhoneNumberDuplicatedException();
        }

        String hashedPassword = BCrypt.hashpw(signUpRequest.getPassword(), BCrypt.gensalt());

        MemberEntity member = MemberEntity.builder()
                .phoneNum(signUpRequest.getPhoneNum())
                .password(hashedPassword)
                .nickname(signUpRequest.getNickname())
                .birthday(birthday)
                .gender(signUpRequest.getGender())
                .build();

        memberRepository.insert(member);

        return true;

    }
}
