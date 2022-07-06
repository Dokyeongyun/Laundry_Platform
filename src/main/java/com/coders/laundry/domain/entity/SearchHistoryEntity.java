package com.coders.laundry.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class SearchHistoryEntity {
    private int searchHistoryId;
    private String keyword;
    private String type;
    private Integer searchMemberId;
    private LocalDateTime createDate;
}
