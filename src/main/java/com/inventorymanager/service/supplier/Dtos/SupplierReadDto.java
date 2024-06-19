package com.inventorymanager.service.supplier.Dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SupplierReadDto {
    private UUID id;
    private String name;
    private String address;
    private String email;
    private String phone;
}
