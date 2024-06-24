package com.inventorymanager.controller;

import com.inventorymanager.controller.shared.SuccessResponseEntity;
import com.inventorymanager.domain.order.Order;
import com.inventorymanager.service.order.Dtos.OrderCreateDto;
import com.inventorymanager.service.order.Dtos.OrderReadDto;
import com.inventorymanager.service.order.IOrdeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    @Autowired
    private IOrdeService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseEntity<OrderReadDto>> getOrderById(@PathVariable UUID id) {
        OrderReadDto orderReadDto = orderService.getOrderById(id);
        SuccessResponseEntity<OrderReadDto> response = new SuccessResponseEntity<>();
        response.setData(new ArrayList<>(List.of(orderReadDto)));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<SuccessResponseEntity<OrderReadDto>> getAllOrders() {
        List<OrderReadDto> orders = orderService.getAllOrders();
        SuccessResponseEntity<OrderReadDto> response = new SuccessResponseEntity<>();
        response.setData(orders);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public OrderReadDto createOrder(@RequestBody @Valid OrderCreateDto orderCreateDto) {
//        OrderReadDto orderCreated = orderService.createOrder(orderCreateDto);
//        SuccessResponseEntity<OrderReadDto> response = new SuccessResponseEntity<>();
//        response.setData(new ArrayList<>(List.of(orderCreated)));
//        return ResponseEntity.ok(response);
        return orderService.createOrder(orderCreateDto);
    }
}
