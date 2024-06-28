package com.zippy.trips.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor

public class LlegadaDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    private Long type;

    @NotEmpty
    private Date StartDate;

    @NotEmpty
    private DocumentDTO UserId;

    @NotEmpty
    private Long VehicleId;

    @NotEmpty
    private Long StartStationId;

    @NotEmpty
    private Date MaxDate;



}
