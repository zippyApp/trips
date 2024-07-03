package com.zippy.trips.controller;

import com.zippy.trips.dto.IncomingDTO;
import com.zippy.trips.dto.OutcomingDTO;
import com.zippy.trips.model.Trip;
import com.zippy.trips.service.interfaces.ITripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {
    private ITripService tripService;

    @GetMapping("/{idStation}/incoming")
    public ResponseEntity<List<IncomingDTO>> getIncomingTrips(@PathVariable long idStation) {
        return ResponseEntity.ok(tripService.getIncomingInfo(idStation));
    }

    @GetMapping("/{idStation}/outcoming")
    public ResponseEntity<List<OutcomingDTO>> getOutcomingTrips(@PathVariable long idStation) {
        return ResponseEntity.ok(tripService.getOutcomingInfo(idStation));
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<?> getTripById(@PathVariable Long tripId) {
        return tripService.findById(tripId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{tripId}/status")
    public ResponseEntity<?> updateTripStatus(@PathVariable Long tripId, @RequestParam long value) {
        return tripService.updateStatusTypeId(tripId, value)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{tripId}/cancel")
    public ResponseEntity<Trip> cancelTrip(@PathVariable Long tripId) {
        return tripService.cancelTrip(tripId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{tripId}/start")
    public ResponseEntity<Trip> startTrip(@PathVariable Long tripId) {
        return tripService.startTrip(tripId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{tripId}/end")
    public ResponseEntity<Trip> endTrip(@PathVariable Long tripId) {
        return tripService.endTrip(tripId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/new")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip)
                .map(ResponseEntity.created(null)::body)
                .orElse(ResponseEntity.badRequest().build());
    }

    @Autowired
    public void setTripService(ITripService tripService){
        this.tripService = tripService;
    }

}