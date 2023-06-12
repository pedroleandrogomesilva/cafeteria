package com.maidahealth.cafeteria.dtos;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDto {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal price;

    @NotBlank
    private String category;
}
