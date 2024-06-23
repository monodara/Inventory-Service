package com.inventorymanager.service.order.Dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderCreateDto {
    private LocalDateTime orderDate = LocalDateTime.now();
    private List<OrderItemCreateDto> orderItems;
}
