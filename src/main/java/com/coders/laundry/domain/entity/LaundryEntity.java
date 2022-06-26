package com.coders.laundry.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LaundryEntity {
    private int laundryId;
    private String name;
    private String jibunAddress;
    private String jibunAddressDetail;
    private String doroAddress;
    private String doroAddressDetail;
    private double latitude;
    private double longitude;
    private boolean partnership;
}