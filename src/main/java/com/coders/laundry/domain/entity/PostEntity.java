package com.coders.laundry.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostEntity {
    private int postId;
    private int categoryId;
    private int writer;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private int likeNum;
    private LocalDateTime updateDate;

}
