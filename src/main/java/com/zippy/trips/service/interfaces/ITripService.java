package com.zippy.trips.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.zippy.trips.dto.IncomingDTO;
import com.zippy.trips.dto.OutcomingDTO;
import com.zippy.trips.model.Trip;

public interface ITripService {
    Trip createTrip(Trip trip);

    Optional<Trip> findById(Long id);

    List<IncomingDTO> getIncomingInfo(long stationId);

    List<OutcomingDTO> getOutcomingInfo(long stationId);

    Optional<Trip> cancelTrip(Long tripId);

    Optional<Trip> startTrip(Long tripId);

    Optional<Trip> updateStatusTypeId(long id, long tripStatusId);

    Optional<Trip> endTrip(Long tripId);

    List<Trip> findByUserId(Long userId);

    Trip createAndSaveTrip(Trip trip);
}
