package com.inventorymanager.service.order.Dtos;

import com.inventorymanager.domain.order.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderReadDto {
    private UUID id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<OrderItemReadDto> orderItems;
}
