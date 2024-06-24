package com.inventorymanager.service.stock.Dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StockUpdateDto {
    @NotNull
    private String productId;
    @NotNull
    @Min(value = 0, message = "Quantity must be zero or greater")
    private int quantity;
    @NotNull
    private UUID supplierId;
}
