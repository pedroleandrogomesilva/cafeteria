package com.maidahealth.cafeteria.services;

import com.maidahealth.cafeteria.dtos.ItemDto;
import com.maidahealth.cafeteria.dtos.OrderStatusDto;
import com.maidahealth.cafeteria.enums.EnumStatusOrder;
import com.maidahealth.cafeteria.exceptions.ProductNotFoundException;
import com.maidahealth.cafeteria.models.ItemModel;
import com.maidahealth.cafeteria.models.OrderModel;
import com.maidahealth.cafeteria.models.ProductModel;
import com.maidahealth.cafeteria.repositories.OrderRepository;
import com.maidahealth.cafeteria.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    final ProductRepository productRepository;

    final OrderRepository orderRepository;

    final ProductService productService;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public OrderModel createOrderModel(List<ItemDto> itemDtoList) {
        Optional<ProductModel> productModelOptional;
        var orderModel = new OrderModel();
        Double total = 0D;
        List<ItemModel> itemModelList = new ArrayList<>();

        for (ItemDto item : itemDtoList) {
            productModelOptional = productService.findByName(item.getName());
            var itemModel = new ItemModel();
            if (!productModelOptional.isPresent()) {
                throw new ProductNotFoundException(item.getName() + " is a product not found.");
            }
            total += productModelOptional.get().getPrice().doubleValue() * item.getQuantity();
            BeanUtils.copyProperties(item, itemModel);
            itemModelList.add(itemModel);
        }

        orderModel.setStatus(EnumStatusOrder.ABERTO);
        orderModel.setItemModelList(itemModelList);
        orderModel.setTotal(BigDecimal.valueOf(total));
        return orderRepository.save(orderModel);
    }

    public List<OrderModel> findAll() {
        return orderRepository.findAll();
    }

    public Optional<OrderModel> findById(UUID uuid) {
        return orderRepository.findById(uuid);
    }

    public OrderModel changeStatusOrder(UUID uuid, OrderStatusDto orderStatusDto) {
        Optional<OrderModel> orderModelOptional = Optional.ofNullable(orderRepository.findById(uuid).orElseThrow(() -> new ProductNotFoundException("order not found.")));

        validateChangeStatus(orderStatusDto.getStatus(), orderModelOptional.get().getStatus());

        OrderModel orderModel = orderModelOptional.get();
        orderModel.setStatus(orderStatusDto.getStatus());
        return orderRepository.save(orderModel);
    }

    private boolean validateChangeStatus(EnumStatusOrder enumStatusOrderDto, EnumStatusOrder enumStatusOrder){

        if(!enumStatusOrder.isAllowChangeStatus()){
            throw new ProductNotFoundException("current order status cannot be changed");
        }

        if (enumStatusOrderDto.name().equals(enumStatusOrder.name())) {
            throw new ProductNotFoundException("the order already has the status " + enumStatusOrderDto.name());
        }

        return true;
    }
}
