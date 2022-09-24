package com.coders.laundry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class SearchHistory {
    private int searchHistoryId;
    private String keyword;
    private String type;
    private LocalDateTime createdAt;
}
