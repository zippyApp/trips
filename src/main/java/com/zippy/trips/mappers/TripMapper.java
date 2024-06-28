package com.zippy.trips.mappers;

import com.zippy.trips.model.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper de estaciones
 *
 * @author Johan Jerez
 *
 */
@Mapper(componentModel = "spring")
public interface TripMapper {

    //@Mapping(source="stationStatus.id", target="stationStatus.id")
    //@Mapping(source= "stationStatus.stationName", target="stationStatus.stationName")
    Trip toTripDTO(Trip station);

}
