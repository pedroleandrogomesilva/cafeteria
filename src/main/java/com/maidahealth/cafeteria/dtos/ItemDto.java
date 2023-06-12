package com.maidahealth.cafeteria.dtos;

import com.maidahealth.cafeteria.models.ItemModel;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class ItemDto {

    private String name;

    private Integer quantity;

}
