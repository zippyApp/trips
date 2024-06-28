package com.zippy.trips.repository;

import com.zippy.trips.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de ESTACIONES
 *
 * @author Johan Jerez
 * @since 1.0
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByTripTypeId(Long tripTypeId);
    @Query("SELECT t FROM Trip t WHERE t.tripStatusId = :tripStatusId AND t.tripStartStationId = :tripStartStationId")
    List<Trip> findByTripStatusIdAndTripStartStationId(@Param("tripStatusId") Long tripStatusId, @Param("tripStartStationId") Long tripStartStationId);

    @Query("SELECT t FROM Trip t WHERE t.tripStatusId = :tripStatusId AND t.tripEndStationId = :tripEndStationId")
    List<Trip> findByTripStatusIdAndTripEndStationId(@Param("tripStatusId") Long tripStatusId, @Param("tripEndStationId") Long tripStartStationId);

}

