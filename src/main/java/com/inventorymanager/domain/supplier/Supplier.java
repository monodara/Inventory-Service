package com.inventorymanager.domain.supplier;

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
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(columnDefinition = "VARCHAR(20)", length = 20, nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(100)", length = 100)
    private String address;

    @Column(columnDefinition = "VARCHAR(20)", length = 20)
    private String email;

    @Column(columnDefinition = "VARCHAR(11)",length = 11)
    private String phone;

}
