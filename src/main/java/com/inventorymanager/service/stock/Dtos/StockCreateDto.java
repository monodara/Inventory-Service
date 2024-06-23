package com.inventorymanager.service.stock.Dtos;

import com.inventorymanager.domain.supplier.Supplier;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StockCreateDto {
    @NotNull
    private String productId;
    @NotNull
    @Positive(message = "Quantity must be positive.")
    private int quantity;
    @NotNull
    private UUID supplierId;
}
