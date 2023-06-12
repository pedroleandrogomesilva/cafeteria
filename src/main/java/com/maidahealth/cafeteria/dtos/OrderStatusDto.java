package com.maidahealth.cafeteria.dtos;

import com.maidahealth.cafeteria.enums.EnumStatusOrder;
import lombok.Data;

@Data
public class OrderStatusDto {

    EnumStatusOrder status;

}
