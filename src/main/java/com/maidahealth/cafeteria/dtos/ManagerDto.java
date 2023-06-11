package com.maidahealth.cafeteria.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ManagerDto {

    @NotBlank
    private String name;

    @NotBlank
    private String establishmentName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
