package com.inventorymanager.domain.order;

import com.inventorymanager.domain.supplier.Supplier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private OrderStatus status = OrderStatus.PROCESSING;
}
