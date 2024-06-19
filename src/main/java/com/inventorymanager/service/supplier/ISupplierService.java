package com.inventorymanager.service.supplier;

import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;

import java.util.List;
import java.util.UUID;

public interface ISupplierService {
    public SupplierReadDto getSupplierById(UUID id);
    public List<SupplierReadDto> getAllSuppliers();
    public Supplier createSupplier(SupplierCreateDto supplierCreateDto);
    public Supplier updateSupplier(UUID id, Supplier newSupplier);
    public void deleteSupplier(UUID id);
}
