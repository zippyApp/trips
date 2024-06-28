package com.zippy.trips.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * DTO para exclusivamnete renderizar las estaciones en el mapa
 *
 * @author Johan Jerez
 * @since 1.0
 */
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
