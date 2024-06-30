package com.inventorymanager.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inventorymanager.domain.stock.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY) //id only
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY) //id only
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(columnDefinition = "INTEGER CHECK (quantity >= 0)", nullable = false)
    private int quantity;

    @Column(columnDefinition = "NUMERIC")
    private double sellingPrice;
}
