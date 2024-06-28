package com.zippy.trips.clients;

import com.zippy.trips.dto.VehicleDTO;
import com.zippy.trips.dto.VehicleStatusDTO;
import com.zippy.trips.dto.VehicleTypeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(value = "vehiclesFeign", url = "http://localhost:8082/api/v1/vehicles")
public interface VehiclesClient {

    @GetMapping("/all")
    List<VehicleDTO> findAll();

    @GetMapping("/{id}")
    VehicleDTO findById(@PathVariable Long id);

    @PutMapping("/{vehicleId}/status/{statusId}")
    VehicleDTO updateVehicleStatus(@PathVariable Long vehicleId, @PathVariable Integer statusId);

    @GetMapping("/status/{id}")
    List<VehicleDTO> findByVehicleStatusId(@PathVariable Long id);

    @GetMapping("/station/{stationId}")
    List<VehicleDTO> findByStationId(@PathVariable Long stationId);

    @GetMapping("/status/all")
    List<VehicleStatusDTO> getAllVehicleStatus();

    @GetMapping("/type/all")
    List<VehicleTypeDTO> findAllVehicleType();

    @GetMapping("/type/{name}")
    List<VehicleDTO>findByVehicleTypeName(@PathVariable String name);

    @GetMapping("/station/{stationId}/status/{name}")
    List<VehicleDTO>findByVehicleStationIdAndStatusName(@PathVariable Long stationId, @PathVariable String name);
}
