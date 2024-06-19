package com.inventorymanager.domain.supplier;

import java.util.List;
import java.util.UUID;

public interface ISupplierRepo {
    public Supplier getSupplierById(UUID id);
    public List<Supplier> getAllSuppliers();
    public Supplier createSupplier(Supplier supplier);
    public Supplier updateSupplier(Supplier newSupplier);
    public void deleteSupplier(UUID id);
}
