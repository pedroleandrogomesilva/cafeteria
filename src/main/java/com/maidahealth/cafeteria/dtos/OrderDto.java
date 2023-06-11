package com.maidahealth.cafeteria.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {

    @NotBlank
    private List<ItemDto> itemDtoList;

    @NotBlank
    private BigDecimal total;

}
