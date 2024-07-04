package com.zippy.trips.service.impl;

import com.zippy.trips.clients.RoutesClient;
import com.zippy.trips.clients.UsersClient;
import com.zippy.trips.clients.VehiclesClient;
import com.zippy.trips.dto.*;
import com.zippy.trips.mappers.TripMapper;
import com.zippy.trips.model.Trip;
import com.zippy.trips.repository.TripRepository;
import com.zippy.trips.service.interfaces.ITripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TripService implements ITripService {
    private TripRepository tripRepository;
    private RoutesClient routesClient;
    private UsersClient usersClient;
    private VehiclesClient vehiclesClient;

    //Trip status ids
    private final long RESERVED = 1L;
    private final long WAITING_APPROVAL = 2L;
    private final long IN_PROGRESS = 3L;
    private final long COMPLETED = 4L;
    private final long CANCELED_BY_USER = 5L;
    private final long CANCELED_BY_SYSTEM = 6L;

    //Trip type ids
    private final long RESERVED_TRIP = 1;
    private final long INSTANT_TRIP = 2L;

    //Vehicle status ids
    private final int AVAILABLE = 1;
    private final int RESERVED_VEHICLE = 2;
    private final int ON_TRIP = 3;

    @Transactional
    @Override
    public Trip createAndSaveTrip(Trip trip) {
        return tripRepository.save(createTrip(trip));
    }

    @Override
    @Transactional
    public List<Trip> findByUserId(Long userId) {
        return findByUserId(userId);
    }

    @Transactional
    @Override
    public Trip createTrip(Trip trip) {
        trip.setWaitingApprovalDate(new Date());
        trip.setVehicleId(
                vehiclesClient.findByStationIdAndStatusId(trip.getStartStationId(), AVAILABLE)
                        .stream()
                        .findFirst()
                        .map(VehicleDTO::getId)
                        .orElseThrow(() -> new RuntimeException("No available vehicles"))
        );
        trip.setCost(
                routesClient.getRouteInfo(trip.getStartStationId(), trip.getEndStationId()).getDistance() * 1000
        );
        trip.setEndDate(
                Date.from(
                        LocalDateTime.now()
                                .plusMinutes(routesClient.getRouteInfo(trip.getStartStationId(), trip.getEndStationId())
                                        .getDuration() + 10
                                ).atZone(ZoneId.systemDefault()).toInstant()
                )
        );
        if (trip.getTypeId() == INSTANT_TRIP) {
            trip.setStatusId(WAITING_APPROVAL);

        }
        else {
            trip.setStatusId(RESERVED);
            trip.setTypeId(RESERVED_TRIP);
            trip.setEndDate(
                    Date.from(
                            trip.getEndDate().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                                    .plusMinutes(20)
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant()
                    ));
            trip.setWaitingApprovalDate(
                    Date.from(
                            trip.getWaitingApprovalDate().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                                    .plusMinutes(20)
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant()
                    )
            );
        }
        vehiclesClient.updateVehicleStatus(trip.getVehicleId(), RESERVED_VEHICLE);
        return trip;
    }

    @Transactional
    @Override
    public List<IncomingDTO> getIncomingInfo(long station) {
        return tripRepository.findByTripStatusIdAndTripEndStationId(IN_PROGRESS, station).stream()
                .map(this::mapToIncomingDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<OutcomingDTO> getOutcomingInfo(long station) {
        return Stream.concat(
                        tripRepository.findByTripStatusIdAndTripStartStationId(RESERVED, station).stream(),
                        tripRepository.findByTripStatusIdAndTripStartStationId(WAITING_APPROVAL, station).stream()
                )
                .map(this::mapToOutcomingDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<Trip> findById(Long id) {
        return tripRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Trip> cancelTrip(Long id) {
        return tripRepository.findById(id).flatMap(trip -> {
            if (trip.getStatusId() == RESERVED) {
                trip.setStatusId(CANCELED_BY_USER);
                tripRepository.save(trip);
                return Optional.of(trip);
            }
            return Optional.empty();
        });
    }

    @Transactional
    @Override
    public Optional<Trip> startTrip(Long id) {
        return tripRepository.findById(id).flatMap(trip -> {
            if (trip.getStatusId() == WAITING_APPROVAL) {
                trip.setStatusId(IN_PROGRESS);
                trip.setStartDate(new Date());
                vehiclesClient.updateVehicleStatus(trip.getVehicleId(), ON_TRIP);
                vehiclesClient.updateVehicleStation(trip.getVehicleId(),trip.getStartStationId());
                tripRepository.save(trip);
                return Optional.of(trip);
            }
            return Optional.empty();
        });
    }

    @Transactional
    @Override
    public Optional<Trip> endTrip(Long id) {
        return tripRepository.findById(id).flatMap(trip -> {
            if (trip.getStatusId() == IN_PROGRESS) {
                trip.setStatusId(COMPLETED);
                trip.setEndDate(new Date());
                vehiclesClient.updateVehicleStatus(trip.getVehicleId(), AVAILABLE);
                tripRepository.save(trip);
                return Optional.of(trip);
            }
            return Optional.empty();
        });
    }

    @Transactional
    @Override
    public Optional<Trip> updateStatusTypeId(long id, long tripStatusId) {
        return tripRepository.findById(id).flatMap(trip -> {
            if (isValidStatusTransition(trip.getStatusId(), tripStatusId)) {
                trip.setStatusId(tripStatusId);
                if (tripStatusId == IN_PROGRESS) {
                    trip.setStartDate(new Date());
                } else if (tripStatusId == COMPLETED) {
                    trip.setEndDate(new Date());
                }
                tripRepository.save(trip);
                return Optional.of(trip);
            }
            return Optional.empty();
        });
    }

    private boolean isValidStatusTransition(long currentStatus, long newStatus) {
        return (currentStatus == RESERVED && newStatus == WAITING_APPROVAL) ||
                (currentStatus == WAITING_APPROVAL && newStatus == IN_PROGRESS) ||
                (currentStatus == IN_PROGRESS && newStatus == COMPLETED);
    }



    private IncomingDTO mapToIncomingDTO(Trip trip) {
        String fullName = usersClient.getUserById(trip.getUserId()).getFirstNames() + " " + usersClient.getUserById(trip.getUserId()).getLastNames();
        int duration = routesClient.getRouteInfo(trip.getStartStationId(), trip.getEndStationId()).getDuration() + 10;
        Date maxDate = calculateMaxDate(trip.getStartDate(), duration);

        return IncomingDTO.builder()
                .maxToArribe(maxDate)
                .startDate(trip.getStartDate())
                .startStationId(trip.getStartStationId())
                .document(usersClient.getUserById(trip.getUserId()).getDocument())
                .fullName(fullName)
                .typeId(trip.getTypeId())
                .userId(trip.getUserId())
                .vehicleId(trip.getVehicleId())
                .build();
    }

    private OutcomingDTO mapToOutcomingDTO(Trip trip) {
        String fullName = usersClient.getUserById(trip.getUserId()).getFirstNames() + " " + usersClient.getUserById(trip.getUserId()).getLastNames();
        int duration = routesClient.getRouteInfo(trip.getStartStationId(), trip.getEndStationId()).getDuration() + 10;
        Date maxDate = calculateMaxDate(trip.getStartDate(), duration);

        return OutcomingDTO.builder()
                .maxToBoard(maxDate)
                .endDate(trip.getEndDate())
                .endStationId(trip.getEndStationId())
                .document(usersClient.getUserById(trip.getUserId()).getDocument())
                .fullName(fullName)
                .typeId(trip.getTypeId())
                .userId(trip.getUserId())
                .vehicleId(trip.getVehicleId())
                .build();
    }

    private Date calculateMaxDate(Date startDate, int duration) {
        LocalDateTime startDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime maxDateTime = startDateTime.plusMinutes(duration);
        return Date.from(maxDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Autowired
    public void setTripRepository(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Autowired
    public void setRoutesClient(RoutesClient routesClient) {
        this.routesClient = routesClient;
    }

    @Autowired
    public void setUsersClient(UsersClient usersClient) {
        this.usersClient = usersClient;
    }

    @Autowired
    public void setVehiclesClient(VehiclesClient vehiclesClient) {
        this.vehiclesClient = vehiclesClient;
    }
}








