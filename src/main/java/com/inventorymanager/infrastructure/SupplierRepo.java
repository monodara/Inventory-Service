package com.inventorymanager.infrastructure;

import com.inventorymanager.domain.supplier.ISupplierRepo;
import com.inventorymanager.domain.supplier.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SupplierRepo implements ISupplierRepo {
    @Autowired
    private ISupplierJpaRepo supplierJpaRepo;
    @Override
    public Supplier getSupplierById(UUID id) {
        return supplierJpaRepo.getById(id);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return null;
    }

    @Override
    public Supplier createSupplier(Supplier Supplier) {
        return null;
    }

    @Override
    public Supplier updateProduct(UUID id, Supplier newSupplier) {
        return null;
    }

    @Override
    public void deleteSupplier(UUID id) {

    }
}
