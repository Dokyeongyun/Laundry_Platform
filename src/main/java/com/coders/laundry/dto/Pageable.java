package com.coders.laundry.dto;

import com.coders.laundry.common.validation.ValidEnum;
import com.coders.laundry.domain.enums.SortType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
public class Pageable {

    @PositiveOrZero
    private int offset;

    @Range(min = 0, max = 100)
    private int limit;

    private String sort;

    @ValidEnum(enumClass = SortType.class)
    private String sortType;

    public Pageable() {
        this.sortType = "asc";
    }
}
