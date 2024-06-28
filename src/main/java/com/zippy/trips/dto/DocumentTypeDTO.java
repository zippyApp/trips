package com.zippy.trips.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DocumentTypeDTO {

    @NotNull
    private Integer id;

    private String name;

}
