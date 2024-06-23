package com.inventorymanager.service.stock.Dtos;

import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StockReadDto {
    private UUID id;
    private String productId;
    private int quantity;
    private SupplierReadDto supplier;
}
