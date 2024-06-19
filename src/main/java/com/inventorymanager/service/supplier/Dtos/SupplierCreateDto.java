package com.inventorymanager.service.supplier.Dtos;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierCreateDto {
    private String name;
    private String address;
    private String email;
    private String phone;
}
