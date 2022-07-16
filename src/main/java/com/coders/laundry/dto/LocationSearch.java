package com.coders.laundry.dto;

import lombok.Data;

@Data
public class LocationSearch {
    private Point baseLocation;
    private int radius;
}
