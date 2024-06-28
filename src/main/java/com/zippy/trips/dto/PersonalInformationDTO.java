package com.zippy.trips.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PersonalInformationDTO {
    private Long id;

    @NotEmpty
    private String firstNames;

    @NotEmpty
    private String lastNames;

    @NotEmpty
    private String occupation;

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private DocumentDTO document;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String birthDate;

    @NotNull
    private ReferenceDTO reference;
}
