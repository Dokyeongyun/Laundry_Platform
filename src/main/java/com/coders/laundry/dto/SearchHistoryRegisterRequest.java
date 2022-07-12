package com.coders.laundry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchHistoryRegisterRequest {
    private String keyword;
    private String type;
}
