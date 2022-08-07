package com.coders.laundry.dto;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private int totalCount;
    private int offset;
    private int limit;
    private String sort;
    private String sortType;
    private List<T> list;

    public Page(int totalCount, Pageable pageable, List<T> list) {
        this.totalCount = totalCount;
        this.offset = pageable.getOffset();
        this.limit = pageable.getLimit();
        this.sort = pageable.getSort();
        this.sortType = pageable.getSortType();
        this.list = list;
    }
}
