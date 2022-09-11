package com.coders.laundry.domain.entity;

import com.coders.laundry.domain.enums.DeviceType;
import java.time.Instant;
import lombok.Data;

@Data
public class DeviceTokenEntity {

    private int memberId;

    private String token;

    private DeviceType type;

    private Instant createdAt;
}
