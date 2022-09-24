package com.coders.laundry.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Page<T> {
    private int totalCount;
    @JsonUnwrapped
    private Pageable pageable;
    private List<T> list;
}
