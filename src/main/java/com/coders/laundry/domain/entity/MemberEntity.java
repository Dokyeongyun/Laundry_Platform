package com.coders.laundry.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MemberEntity {
    private int memberId;
    private String phoneNum;
    private String password;
    private String nickname;
    private LocalDate birthday;
    private String gender;
    private boolean autoLoginYn;
    private LocalDateTime joinDate;
    private LocalDateTime withdrawalDate;
}
