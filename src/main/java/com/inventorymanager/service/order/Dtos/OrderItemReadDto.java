package com.inventorymanager.service.order.Dtos;

import com.inventorymanager.domain.order.Order;
import com.inventorymanager.domain.stock.Stock;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemReadDto {
    private UUID id;
    private UUID stockId;
    private int quantity;
}
