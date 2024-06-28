package com.zippy.trips.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class SalidaDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private Long type;



    @NotEmpty
    private Date EndDate;

    @NotEmpty
    private DocumentDTO UserId;

    @NotEmpty
    private Long VehicleId;

    @NotEmpty
    private Long EndStationId;

    @NotEmpty
    private Date MaxAbord;


}
