package com.coders.laundry.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class Pageable {
    private int offset;
    private int limit;
    private String sort;
    private String sortType;
}
