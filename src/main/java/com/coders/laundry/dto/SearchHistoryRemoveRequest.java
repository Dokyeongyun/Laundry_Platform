package com.coders.laundry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryRemoveRequest {
    private int searchHistoryId;
}
