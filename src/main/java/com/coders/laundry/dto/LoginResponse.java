package com.coders.laundry.dto;

import com.coders.laundry.domain.entity.MemberEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    MemberEntity memberEntity;
    String token;

}