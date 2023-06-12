package com.maidahealth.cafeteria.models;

import com.maidahealth.cafeteria.enums.EnumStatusOrder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "order_delivery")
public class OrderModel extends RepresentationModel<ManagerModel> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemModel> itemModelList;

    @Column(nullable = false)
    private BigDecimal total;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumStatusOrder status;

    public void addItemList(ItemModel itemModel) {
        itemModelList.add(itemModel);
    }
}
