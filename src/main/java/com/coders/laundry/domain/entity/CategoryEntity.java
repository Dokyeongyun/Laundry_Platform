package com.coders.laundry.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryEntity {
    private int categoryId;
    private String categoryName;
}
