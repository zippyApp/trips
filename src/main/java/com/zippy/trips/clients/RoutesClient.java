package com.zippy.trips.clients;


import com.fasterxml.jackson.databind.JsonNode;
import com.zippy.trips.dto.RouteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "routesFeign", url = "http://localhost:8083/api/v1/route")
public interface RoutesClient {

    @GetMapping("/toOrigin/{userCoordinates}/{originStationId}")
    JsonNode getRouteUserToOrigin(@PathVariable("userCoordinates") String userCoordinates, @PathVariable("originStationId") Long originStationId);

    @GetMapping("/toDestination/{originStationId}/{destinationStationId}")
    JsonNode getRouteOriginToDestination(@PathVariable("originStationId") Long originStationId, @PathVariable("destinationStationId") Long destinationStationId);

    @GetMapping("/information/{originStationId}/{destinationStationId}")
    RouteDTO getRouteInformation(@PathVariable("originStationId") Long originStationId, @PathVariable("destinationStationId") Long destinationStationId);

}
