package com.zippy.trips.clients;

import com.zippy.trips.dto.StationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "stationsFeign", url = "http://localhost:9000/api/v1/stations")
public interface StationsClient {

    @GetMapping("/all")
    List<StationDTO> getStations();

    @GetMapping("/{stationId}")
    StationDTO getStationById(@PathVariable(name = "stationId") String stationId);

    @PutMapping("/update/status")
    StationDTO updateStationStatus(@RequestHeader("stationId") Long stationId, @RequestHeader("statusId")Long statusId);

    @PutMapping("/update/capacity")
    StationDTO updateStationCapacity(@RequestHeader("stationId") Long stationId, @RequestHeader("capacity") Integer capacity);

}
