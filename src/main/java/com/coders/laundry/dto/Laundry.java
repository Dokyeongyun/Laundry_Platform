package com.coders.laundry.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Laundry {

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
