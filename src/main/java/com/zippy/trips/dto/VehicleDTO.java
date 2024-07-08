package com.zippy.trips.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleDTO {
    private Long id;
    private Integer typeId;
    private String status;
    private Long stationId;
    private boolean electric;
    private int batteryLevel;
}
