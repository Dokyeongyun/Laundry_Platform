package com.coders.laundry.domain.entity;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaundryEntity {
    // table's columns
    private int laundryId;
    private String name;
    private String jibunAddress;
    private String jibunAddressDetail;
    private String doroAddress;
    private String doroAddressDetail;
    private double latitude;
    private double longitude;
    private boolean partnership;

    // additional columns
    private String thumbnailImage;
    private double ratingPoint;
    private int reviewCount;
    private List<String> tags;
    private int distance;
    private boolean like;
}