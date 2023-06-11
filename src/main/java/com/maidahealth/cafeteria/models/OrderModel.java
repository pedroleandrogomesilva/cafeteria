package com.maidahealth.cafeteria.models;

import com.maidahealth.cafeteria.enums.EnumStatusOrder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "order_delivery")
public class OrderModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany
    private List<ItemModel> itemModelList;

    @Column(nullable = false)
    private BigDecimal total;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumStatusOrder status;
}
