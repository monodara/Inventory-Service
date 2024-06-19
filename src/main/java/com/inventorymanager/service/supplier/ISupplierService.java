package com.inventorymanager.service.supplier;

import com.inventorymanager.domain.supplier.Supplier;

import java.util.List;
import java.util.UUID;

public interface ISupplierService {
    public Supplier getSupplierById(UUID id);
    public List<Supplier> getAllSuppliers();
    public Supplier createSupplier(Supplier supplier);
    public Supplier updateProduct(UUID id, Supplier newSupplier);
    public void deleteSupplier(UUID id);
}
