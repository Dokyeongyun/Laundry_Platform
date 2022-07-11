package com.coders.laundry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SearchHistory {
    private int searchKeywordId;
    private String keyword;
    private String type;
    private LocalDateTime createdAt;
}
