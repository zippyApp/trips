package com.zippy.trips.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class OutcomingDTO {

    @NotEmpty
    private Date endDate;

    @NotEmpty
    private Long endStationId;

    @NotEmpty
    private Date maxToBoard;

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
