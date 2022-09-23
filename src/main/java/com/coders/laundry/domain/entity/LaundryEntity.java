package com.coders.laundry.domain.entity;

import com.coders.laundry.dto.Laundry;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaundryEntity {
    // table's columns
    private int laundryId;
    private String name;
    private String jibunAddress;
    private String jibunAddressDetail;
    private String doroAddress;
    private String doroAddressDetail;
    private double latitude;
    private double longitude;
    private boolean partnership;

    // additional columns
    private String thumbnailImage;
    private double ratingPoint;
    private int reviewCount;
    private List<String> tags;
    private int distance;
    private boolean like;

    public static Laundry toDto(LaundryEntity entity) {
        if (entity == null) {
            return null;
        }

        return Laundry.builder()
                .laundryId(entity.laundryId)
                .name(entity.name)
                .jibunAddress(entity.jibunAddress)
                .jibunAddressDetail(entity.jibunAddressDetail)
                .doroAddress(entity.doroAddress)
                .doroAddressDetail(entity.doroAddressDetail)
                .latitude(entity.latitude)
                .longitude(entity.longitude)
                .partnership(entity.partnership)
                .build();
    }
}