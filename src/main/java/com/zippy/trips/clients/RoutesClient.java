package com.zippy.trips.clients;


import com.fasterxml.jackson.databind.JsonNode;
import com.zippy.trips.dto.RouteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "routesFeign", url = "http://localhost:9000/api/v1/route")
public interface RoutesClient {

    @GetMapping("/user-to-origin/{userCoordinates}/{originStationId}")
    JsonNode getUserToOrigin(@PathVariable("userCoordinates") String userCoordinates, @PathVariable("originStationId") Long originStationId);

    @GetMapping("/origin-to-destination/{originStationId}/{destinationStationId}")
    JsonNode getOriginToDestination(@PathVariable("originStationId") Long originStationId, @PathVariable("destinationStationId") Long destinationStationId);

    @GetMapping("/info/{originStationId}/{destinationStationId}")
    RouteDTO getRouteInfo(@PathVariable("originStationId") Long originStationId, @PathVariable("destinationStationId") Long destinationStationId);

}
