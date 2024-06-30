package com.inventorymanager.service.stock.Dtos;

import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;
import com.opencsv.bean.CsvIgnore;
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
    private double inputPrice;
    @CsvIgnore
    private SupplierReadDto supplier;
}
