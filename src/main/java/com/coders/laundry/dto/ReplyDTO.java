package com.coders.laundry.dto;

import com.coders.laundry.domain.entity.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {
    private ReplyEntity replyEntity;


}
