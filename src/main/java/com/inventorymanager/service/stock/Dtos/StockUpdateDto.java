package com.inventorymanager.service.stock.Dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateDto {
    @NotNull
    @Min(value = 0, message = "Quantity must be zero or greater")
    private int quantity;
}
