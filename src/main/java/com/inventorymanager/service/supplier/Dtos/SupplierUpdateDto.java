package com.inventorymanager.service.supplier.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierUpdateDto {
    @NotNull
    @Size(min = 4, max = 20, message = "The length should be 4-20")
    private String name;

    @NotNull
    private String address;

    @NotNull
    @Email
    private String email;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number should be 10 to 15 digits")
    private String phone;

    @NotNull
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit"
    )
    private String password;
}
