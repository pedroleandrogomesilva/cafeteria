package com.maidahealth.cafeteria.dtos;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class ItemDto {

    @NotBlank
    private String name;

    @NotBlank
    private Integer quantity;

    @NotBlank
    private BigDecimal price;

    @NotBlank
    private String category;

}
