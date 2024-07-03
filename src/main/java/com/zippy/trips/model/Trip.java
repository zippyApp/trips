package com.zippy.trips.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
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
    private Long typeId;

    @Column(name = "trip_status_id")
    private Long statusId;

    @Column(name = "personal_information_id")
    private Long userId;

    @Column(name="vehicle_id")
    private Long vehicleId;

    @Column(name = "start_station_id")
    private Long startStationId;

    @Column(name = "end_station_id")
    private Long endStationId;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "reservation_date")
    private Date reserveDate;

    @Column(name = "waiting_approval_date")
    private Date waitingApprovalDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name= "score")
    private Integer score;

    @Column(name = "comments")
    private String comments;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_type_id", insertable = false, updatable = false)
    private TripType tripType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_status_id", insertable = false, updatable = false)
    private TripStatus tripStatus;
}
