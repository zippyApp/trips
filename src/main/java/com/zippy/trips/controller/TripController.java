package com.zippy.trips.controller;

import com.zippy.trips.clients.StationsClient;
import com.zippy.trips.dto.LlegadaDTO;
import com.zippy.trips.dto.SalidaDTO;
import com.zippy.trips.dto.StationDTO;
import com.zippy.trips.model.Trip;
import com.zippy.trips.service.impl.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {


    private TripService tripService;

    private StationsClient stationsClient;

    @GetMapping
    public List<StationDTO> pruebafeignStation() {
        return stationsClient.getStations();
    }

    @GetMapping("llegadas/{idEstacion}")
    public ResponseEntity<List<LlegadaDTO>> getLlegadasById(@PathVariable long idEstacion){

        return new ResponseEntity<>(tripService.getInformacionLlegadas(idEstacion), HttpStatus.OK);



    }

    @GetMapping("salidas/{idEstacion}")
    public ResponseEntity<List<SalidaDTO>> getSalidasById(@PathVariable long idEstacion){

        return new ResponseEntity<>(tripService.getInformacionSalidas(idEstacion), HttpStatus.OK);



    }

    @PostMapping("/TripStatusSalida/{tripId}")
    public ResponseEntity<?> updateTripStatusSalida(@PathVariable Long tripId, @RequestParam long value) {
        try {

            Trip trip = tripService.findById(tripId);


            if (trip.getTripStatusId() != 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El estado del viaje no permite la actualización");
            }

            tripService.updateStatusTypeId(tripId, value);

            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la Id del viaje " + tripId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estado invalido: " + value);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }



    @PostMapping("/TripStatusLlegada/{tripId}")
    public ResponseEntity<?> updateTripStatusLlegada(@PathVariable Long tripId, @RequestParam long value) {
        try {

            Trip trip = tripService.findById(tripId);


            if (trip.getTripStatusId() != 3) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El estado del viaje no permite la actualización");
            }

            tripService.updateStatusTypeId(tripId, value);

            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la Id del viaje " + tripId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estado invalido: " + value);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @Autowired
    public void setTripService(TripService tripService){
        this.tripService = tripService;
    }

    @Autowired
    public void setStationsClient(StationsClient stationsClient){
        this.stationsClient = stationsClient;
    }

}