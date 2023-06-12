package com.maidahealth.cafeteria.controllers;

import com.maidahealth.cafeteria.dtos.ItemDto;
import com.maidahealth.cafeteria.dtos.OrderStatusDto;
import com.maidahealth.cafeteria.dtos.ProductDto;
import com.maidahealth.cafeteria.enums.EnumCategoryProduct;
import com.maidahealth.cafeteria.enums.EnumStatusOrder;
import com.maidahealth.cafeteria.exceptions.InvalidCategoryException;
import com.maidahealth.cafeteria.exceptions.ProductNotFoundException;
import com.maidahealth.cafeteria.models.ItemModel;
import com.maidahealth.cafeteria.models.OrderModel;
import com.maidahealth.cafeteria.models.ProductModel;
import com.maidahealth.cafeteria.services.OrderService;
import com.maidahealth.cafeteria.services.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderModel> requestOrder(@RequestBody List<ItemDto> itemDtoList) {
        OrderModel orderModel = orderService.createOrderModel(itemDtoList);
        orderModel.add(linkTo(methodOn(OrderController.class).getOneOrderModel(orderModel.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderModel);
    }

    @GetMapping
    public ResponseEntity<List<OrderModel>> getAllOrders() {
        List<OrderModel> orderModelList = orderService.findAll();

        if (orderModelList.isEmpty()) {
            throw new ProductNotFoundException("list orders not found.");
        } else {
            for (OrderModel orderModel : orderModelList) {
                UUID id = orderModel.getId();
                orderModel.add(linkTo(methodOn(OrderController.class).getOneOrderModel(id)).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(orderModelList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> getOneOrderModel(@PathVariable(value = "id") UUID uuid) {
        Optional<OrderModel> orderModelOptional = orderService.findById(uuid);
        if (!orderModelOptional.isPresent()) {
            throw new ProductNotFoundException("order not found.");
        } else {
            orderModelOptional.get().add(linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(orderModelOptional.get());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderModel> changeStatusOrder(@PathVariable(value = "id") UUID uuid, @RequestBody OrderStatusDto orderStatusDto){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.changeStatusOrder(uuid, orderStatusDto));
    }

}
