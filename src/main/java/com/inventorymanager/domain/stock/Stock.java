package com.inventorymanager.domain.stock;

import com.inventorymanager.domain.supplier.Supplier;
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
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private int quantity;

    //fake product
    @Column(columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
    private String productId;

    @ManyToOne(fetch = FetchType.LAZY) //id only
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
