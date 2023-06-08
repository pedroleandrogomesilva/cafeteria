package com.maidahealth.cafeteria.controllers;

import com.maidahealth.cafeteria.dtos.ProductDto;
import com.maidahealth.cafeteria.models.ProductModel;
import com.maidahealth.cafeteria.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/product")
public class ProductController {

    public static final String DRINK = "bebida";
    public static final String FOOD = "comida";
    public static final String DESSERT = "sobremesa";

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> registerManager(@RequestBody @Valid ProductDto productDto) {
        var productModel = new ProductModel();

        if (!productDto.getCategory().equals(DRINK) && (!productDto.getCategory().equals(FOOD)) && (!productDto.getCategory().equals(DESSERT))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category invalid.");
        }

        BeanUtils.copyProperties(productDto, productModel);
        ProductModel productModelResponse = productService.create(productModel);
        productModel.add(linkTo(methodOn(ProductController.class).getOneProductModel(productModelResponse.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(productModel);
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productModelList = productService.findAll();

        if (productModelList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (ProductModel productModel : productModelList) {
                UUID id = productModel.getId();
                productModel.add(linkTo(methodOn(ProductController.class).getOneProductModel(id)).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(productModelList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProductModel(@PathVariable(value = "id") UUID uuid) {
        Optional<ProductModel> productModelOptional = productService.findById(uuid);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        } else {
            productModelOptional.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneProductModel(@PathVariable(value = "id") UUID uuid) {
        Optional<ProductModel> productModelOptional = productService.findById(uuid);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        productService.delete(productModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductModel(@PathVariable(value = "id") UUID uuid, @RequestBody @Valid ProductDto productDto) {
        Optional<ProductModel> productModelOptional = productService.findById(uuid);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        var productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        productModel.setId(productModelOptional.get().getId());
        ProductModel productModelResponse = productService.create(productModel);
        productModel.add(linkTo(methodOn(ProductController.class).getOneProductModel(productModelResponse.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(productModel);
    }
}
