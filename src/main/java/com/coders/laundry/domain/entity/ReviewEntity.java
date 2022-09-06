package com.coders.laundry.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewEntity {

    private Integer reviewId;

    private Integer laundryId;

    private Integer writerId;

    /* Range: 1 ~ 5 */
    private int rating;

    private String contents;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
