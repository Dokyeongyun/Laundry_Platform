package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.dto.LoginResponse;
import com.coders.laundry.dto.Member;
import com.coders.laundry.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final TokenManagerService tokenManagerService;

    public LoginResponse login(String phoneNum, String password) {

        MemberEntity member = memberRepository.selectByPhoneNumber(phoneNum);

        if (member == null||!BCrypt.checkpw(password, member.getPassword())) {
            return null;
        }

        Member memberInfo = Member.builder()
                .memberId(member.getMemberId())
                .phoneNum(member.getPhoneNum())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .autoLoginYn(member.isAutoLoginYn())
                .joinDate(member.getJoinDate())
                .build();

        return LoginResponse.builder()
                .member(memberInfo)
                .accessToken(tokenManagerService.createToken(member.getMemberId()))
                .refreshToken(tokenManagerService.createRefreshToken())
                .build();

    }
}
