package com.coders.laundry.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LaundryLike {

    private int laundryLikeId;

    private int memberId;

    private String nickname;

    private int laundryId;

    private String laundryName;

    private double ratingPoint;

    private int reviewCount;
}
