package com.coders.laundry.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class LaundryVisitHistoryEntity {
    private int visitHistorySeq;
    private int laundryId;
    private int facilityId;
    private int memberId;
    private LocalDateTime visitDate;
}
