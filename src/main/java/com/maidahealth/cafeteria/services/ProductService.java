package com.maidahealth.cafeteria.services;

import com.maidahealth.cafeteria.models.ProductModel;
import com.maidahealth.cafeteria.repositories.ManagerRepository;
import com.maidahealth.cafeteria.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductModel create(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> findById(UUID uuid) {
        return productRepository.findById(uuid);
    }

    public void delete(ProductModel productModel) {
        productRepository.delete(productModel);
    }
}
