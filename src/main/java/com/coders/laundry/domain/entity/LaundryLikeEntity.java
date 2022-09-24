package com.coders.laundry.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LaundryLikeEntity {
    private int laundryLikeId;
    private int memberId;
    private int laundryId;
    private LocalDateTime likeDate;
}
