package com.coders.laundry.domain.entity;

import com.coders.laundry.dto.Review;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
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

    private LocalDate visitDate;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public static Review toDto(ReviewEntity entity) {
        return Review.builder()
                .reviewId(entity.getReviewId())
                .laundryId(entity.getLaundryId())
                .writerId(entity.getWriterId())
                .rating(entity.getRating())
                .contents(entity.getContents())
                .visitDate(entity.getVisitDate())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }

}
