package com.zippy.trips.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StationDTO {

    private Long id;
    private String stationName;
    private String stationAddress;
    private Double latitude;
    private Double longitude;
    private StationStatusDTO stationStatus;
    private Integer capacity;
    private Date lastMaintenance;

}
