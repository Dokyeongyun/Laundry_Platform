package com.coders.laundry.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Member {

    private int memberId;
    private String phoneNum;
    private String nickname;
    private LocalDate birthday;
    private String gender;
    private boolean autoLoginYn;
    private LocalDateTime joinDate;

}
