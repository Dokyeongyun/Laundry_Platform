package com.coders.laundry.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    String phoneNum;
    String password;
}