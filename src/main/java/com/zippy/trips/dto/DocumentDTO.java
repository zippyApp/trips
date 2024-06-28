package com.zippy.trips.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DocumentDTO {
    private Long id;

    @NotEmpty
    private String number;

    @NotNull
    private DocumentTypeDTO type;

    private String frontImage;

    private String backImage;
}
