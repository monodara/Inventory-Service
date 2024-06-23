package com.inventorymanager.service.stock.Dtos;

import com.inventorymanager.domain.supplier.Supplier;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StockCreateDto {
    private UUID product_id;
    private int quantity;
    private Supplier supplier;
}
