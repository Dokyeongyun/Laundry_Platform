package com.coders.laundry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String phoneNum;

    private String password;

    private String nickname;

    private String birthday;

    private String gender;

}
