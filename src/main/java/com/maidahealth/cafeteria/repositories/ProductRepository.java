package com.maidahealth.cafeteria.repositories;

import com.maidahealth.cafeteria.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    Optional<ProductModel> findByName(String productName);

    Double findPriceByName(String productName);

}
