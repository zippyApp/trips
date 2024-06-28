package com.zippy.trips.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "trip_type_id")
    private Long tripTypeId;

    @Column(name = "trip_status_id")
    private Long tripStatusId;

    @Column(name = "profile_id")
    private Long tripUserId;

    @Column(name="vehicle_id")
    private Long tripVehicleId;

    @Column(name = "start_station_id")
    private Long tripStartStationId;

    @Column(name = "end_station_id")
    private Long tripEndStationId;

    @Column(name = "cost")
    private Double tripCost;

    @Column(name = "reservation_date")
    private Date tripReserveDate;

    @Column(name = "waiting_approval_date")
    private Date tripWaitingApprovalDate;

    @Column(name = "start_date")
    private Date tripStartDate;

    @Column(name = "end_date")
    private Date tripEndDate;

    @Column(name= "calification")
    private Integer tripCalification;

    @Column(name = "comments")
    private String tripComments;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_type_id", insertable = false, updatable = false)
    private TripType tripType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_status_id", insertable = false, updatable = false)
    private TripStatus tripStatus;
}
