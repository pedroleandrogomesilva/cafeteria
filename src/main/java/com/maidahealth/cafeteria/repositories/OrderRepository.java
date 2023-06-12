package com.maidahealth.cafeteria.repositories;

import com.maidahealth.cafeteria.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
}
