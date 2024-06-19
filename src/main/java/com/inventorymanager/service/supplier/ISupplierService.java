package com.inventorymanager.service.supplier;

import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;
import com.inventorymanager.service.supplier.Dtos.SupplierUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ISupplierService {
    public SupplierReadDto getSupplierById(UUID id);
    public List<SupplierReadDto> getAllSuppliers();
    public SupplierReadDto createSupplier(SupplierCreateDto supplierCreateDto);
    public SupplierReadDto updateSupplier(UUID id, SupplierUpdateDto newSupplier);
    public void deleteSupplier(UUID id);
}
