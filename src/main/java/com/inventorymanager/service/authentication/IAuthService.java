package com.inventorymanager.service.authentication;

import com.inventorymanager.domain.supplier.SupplierCredential;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;

public interface IAuthService {
    public SupplierReadDto register(SupplierCreateDto newUser);
    public String login(SupplierCredential supplierCredential);
}
