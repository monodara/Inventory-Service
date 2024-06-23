package com.inventorymanager.service.stock.Dtos;

import com.inventorymanager.domain.supplier.Supplier;
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
    private UUID product_id;
    private int quantity;
    private Supplier supplier;
}
