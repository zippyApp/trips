package com.zippy.trips.service.impl;

import com.zippy.trips.clients.RoutesClient;
import com.zippy.trips.clients.UsersClient;
import com.zippy.trips.dto.*;
import com.zippy.trips.model.Trip;
import com.zippy.trips.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RoutesClient routesClient;

    @Autowired
    private UsersClient usersClient;

    public List<LlegadaDTO> getInformacionLlegadas(long estacion) {

        long status = 3;
        List<Trip> llegadas = tripRepository.findByTripStatusIdAndTripEndStationId(status, estacion);
        List<LlegadaDTO> llegadaInfo = new ArrayList<>();

        for (Trip llegada : llegadas) {

            String nombre = usersClient.getUserById(llegada.getTripUserId()).getFirstNames() + " " + usersClient.getUserById(llegada.getTripUserId()).getLastNames();
            DocumentDTO identificacion = usersClient.getUserById(llegada.getTripUserId()).getDocument();

            int duracion = routesClient.getRouteInformation(llegada.getTripStartStationId(), llegada.getTripEndStationId()).getDuration() + 10;
            Date maxDate = CalculateMaxDate(llegada.getTripStartDate(), duracion);

            LlegadaDTO llegadaDTO = new LlegadaDTO(nombre,
                    llegada.getTripTypeId(),
                    llegada.getTripStartDate(),
                    identificacion,
                    llegada.getTripVehicleId(),
                    llegada.getTripStartStationId(),
                    maxDate);


            llegadaInfo.add(llegadaDTO);

        }

        return llegadaInfo;

    }

    public List<SalidaDTO> getInformacionSalidas(long estacion) {
        long status = 1;
        long status2 = 2;

        List<Trip> salidas = tripRepository.findByTripStatusIdAndTripStartStationId(status, estacion);
        salidas.addAll(tripRepository.findByTripStatusIdAndTripStartStationId(status2, estacion));

        List<SalidaDTO> salidasInfo = new ArrayList<>();

        for (Trip salida : salidas) {

            String nombre = usersClient.getUserById(salida.getTripUserId()).getFirstNames() + " " + usersClient.getUserById(salida.getTripUserId()).getLastNames();
            DocumentDTO identificacion = usersClient.getUserById(salida.getTripUserId()).getDocument();


            if (salida.getTripStatusId() == 1) {
                int duracion = 20;
                Date fecha = salida.getTripReserveDate();
                Date maxDate = CalculateMaxDate(salida.getTripWaitingApprovalDate(), duracion);

                SalidaDTO salidaDTO = new SalidaDTO(nombre,
                        salida.getTripTypeId(),
                        fecha,
                        identificacion,
                        salida.getTripVehicleId(),
                        salida.getTripEndStationId(),
                        maxDate);

                salidasInfo.add(salidaDTO);

            }

            if (salida.getTripStatusId() == 2) {
                int duracion = 5;
                Date fecha = salida.getTripWaitingApprovalDate();
                Date maxDate = CalculateMaxDate(fecha, duracion);

                SalidaDTO salidaDTO = new SalidaDTO(nombre,
                        salida.getTripTypeId(),
                        fecha,
                        identificacion,
                        salida.getTripVehicleId(),
                        salida.getTripEndStationId(),
                        maxDate);

                salidasInfo.add(salidaDTO);

            }

        }

        return salidasInfo;
    }


    public Trip findById(Long id) {
        Optional<Trip> tripOptional = tripRepository.findById(id);
        return tripOptional.orElseThrow(() -> new NoSuchElementException("Trip not found with id: " + id));
    }



    public void updateStatusTypeId(long id, long value) {
        Optional<Trip> tripOptional = tripRepository.findById(id);

        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();

            trip.setTripStatusId(value);

            if (value == 3) {
                trip.setTripStartDate(new Date());
            }
            if (value == 4) {
                trip.setTripEndDate(new Date());

            }


        } else {
            throw new NoSuchElementException("Trip not found with id: " + id);
        }
    }


    public Date CalculateMaxDate(Date startDate, int duracion) {

        LocalDateTime startDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();


        LocalDateTime maxDateTime = startDateTime.plusMinutes(duracion);


        Date maxDate = Date.from(maxDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return maxDate;
    }



}








