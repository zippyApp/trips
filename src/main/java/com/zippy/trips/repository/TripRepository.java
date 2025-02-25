package com.zippy.trips.repository;

import com.zippy.trips.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByTripTypeId(Long tripTypeId);
    @Query("SELECT t FROM Trip t WHERE t.statusId = :tripStatusId AND t.startStationId = :tripStartStationId")
    List<Trip> findByTripStatusIdAndTripStartStationId(@Param("tripStatusId") Long tripStatusId, @Param("tripStartStationId") Long tripStartStationId);

    @Query("SELECT t FROM Trip t WHERE t.statusId = :tripStatusId AND t.endStationId = :tripEndStationId")
    List<Trip> findByTripStatusIdAndTripEndStationId(@Param("tripStatusId") Long tripStatusId, @Param("tripEndStationId") Long tripStartStationId);

    @Query("SELECT t FROM Trip t WHERE t.userId = :userId")
    List<Trip> findByUserId(@Param("userId") Long userId);

}

