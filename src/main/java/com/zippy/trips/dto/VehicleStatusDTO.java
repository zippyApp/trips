package com.zippy.trips.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleStatusDTO {
  private int id;
  private String name;
}
