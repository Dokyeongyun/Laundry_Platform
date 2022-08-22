package com.coders.laundry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationSearch {
    private Point baseLocation;
    private int radius;
}
