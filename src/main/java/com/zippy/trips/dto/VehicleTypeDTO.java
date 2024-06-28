package com.zippy.trips.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleTypeDTO {
  private int id;
  private String name;
}
