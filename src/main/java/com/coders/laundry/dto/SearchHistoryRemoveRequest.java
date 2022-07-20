package com.coders.laundry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryRemoveRequest {

    @Positive
    private int searchHistoryId;
}
