package com.inventorymanager.service.order.Dtos;

import com.inventorymanager.domain.order.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderUpdateDto {
    private OrderStatus status;
}
