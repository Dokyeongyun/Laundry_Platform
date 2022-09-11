package com.coders.laundry.domain.dto;

import com.coders.laundry.common.validation.ValidEnum;
import com.coders.laundry.domain.enums.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class DeviceTokenSaveRequestDTO {

    private int memberId;

    private String token;

    @ValidEnum(enumClass = DeviceType.class)
    private String deviceType;
}
