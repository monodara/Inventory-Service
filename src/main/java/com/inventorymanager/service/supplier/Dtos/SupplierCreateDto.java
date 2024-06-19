package com.inventorymanager.service.supplier.Dtos;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierCreateDto {
    @NotNull
    @Size(min = 4, max = 20, message = "The length should be 4-20")
    private String name;

    @NotNull
    private String address;

    @NotNull
    @Email
    private String email;

    private String phone;
}
