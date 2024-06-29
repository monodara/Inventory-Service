package com.inventorymanager.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inventorymanager.domain.supplier.Supplier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
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
    @Column(name = "status", columnDefinition = "VARCHAR(20)", nullable = false)
    private OrderStatus status = OrderStatus.PROCESSING;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime deliverDate;

    @Column(columnDefinition = "VARCHAR(50)")
    private String clientEmail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
