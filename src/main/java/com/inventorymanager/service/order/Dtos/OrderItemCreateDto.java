package com.inventorymanager.service.order.Dtos;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemCreateDto {
    @NotNull
    private UUID stockId;
    @NotNull
    @Positive(message = "The quantity of order item must be greater than 0.")
    private int quantity;
    @Positive
    private double sellingPrice;
}
