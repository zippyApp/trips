package com.zippy.trips.mappers;

import com.zippy.trips.dto.IncomingDTO;
import com.zippy.trips.dto.OutcomingDTO;
import com.zippy.trips.dto.SimpleTripDTO;
import com.zippy.trips.model.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripMapper {
    SimpleTripDTO toSimpleTripDTO(Trip trip);
}
