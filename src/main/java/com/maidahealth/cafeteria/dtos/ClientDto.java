package com.maidahealth.cafeteria.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ClientDto {

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 10)
    private String dateOfBirth;

    @NotBlank
    @Size(max = 11)
    private String phoneNumber;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
