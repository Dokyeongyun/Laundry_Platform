package com.coders.laundry.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class Review {

    private Integer reviewId;

    private Integer laundryId;

    private Integer writerId;

    /* Range: 1 ~ 5 */
    private int rating;

    private String contents;

    private LocalDate visitDate;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
