package com.zippy.trips.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomingDTO {

    @NotEmpty
    private Date startDate;

    @NotEmpty
    private Long startStationId;

    @NotEmpty
    private Date maxToArribe;

    @NotEmpty
    private String fullName;

    @NotEmpty
    private Long typeId;

    @NotEmpty
    private DocumentDTO document;

    @NotEmpty
    private Long userId;

    @NotEmpty
    private Long vehicleId;
}
