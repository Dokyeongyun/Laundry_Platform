package com.coders.laundry.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewEntity {
    private int reviewId;
    private int laundryId;
    private int writer;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private int likeNum;
    private LocalDateTime updateDate;
}
