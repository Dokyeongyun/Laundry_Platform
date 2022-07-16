package com.coders.laundry.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchedLaundry {
    private int laundryId;
    private String name;
    private String jibunAddress;
    private String jibunAddressDetail;
    private String doroAddress;
    private String doroAddressDetail;
    private double latitude;
    private double longitude;
    private boolean partnership;
    private String thumbnailImage;
    private double ratingPoint;
    private int reviewCount;
    private List<String> tags;
    private int distance;
    private boolean like;
}
