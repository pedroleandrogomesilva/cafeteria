package com.maidahealth.cafeteria.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StandardErrorDto {

    private Integer code;

    private String status;

    private String message;

    private LocalDateTime localDateTime;

}
