package com.inventorymanager.service.supplier.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierUpdateDto {
    private String name;
    private String address;
    private String email;
    private String phone;
}
