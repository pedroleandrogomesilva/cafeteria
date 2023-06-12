package com.maidahealth.cafeteria.services;

import com.maidahealth.cafeteria.models.ProductModel;
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

    public Optional<ProductModel> findByName(String productName) {
        return productRepository.findByName(productName);
    }

    public void delete(ProductModel productModel) {
        productRepository.delete(productModel);
    }

    public Double findPriceByName(String productName) {
        return productRepository.findPriceByName(productName);
    }
}
