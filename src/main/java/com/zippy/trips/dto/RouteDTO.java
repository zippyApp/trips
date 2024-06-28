package com.zippy.trips.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteDTO {

    private Long id;

    private Long originStationId;

    private Long destinationStationId;

    private Integer duration;

    private Double distance;
}
