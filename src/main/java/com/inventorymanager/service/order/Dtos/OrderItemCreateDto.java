package com.inventorymanager.service.order.Dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemCreateDto {
    private UUID stockId;
    private int quantity;
}
