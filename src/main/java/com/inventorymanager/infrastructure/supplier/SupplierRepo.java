package com.inventorymanager.infrastructure.supplier;

import com.inventorymanager.domain.exception.ResourceNotFoundException;
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
        return supplierJpaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierJpaRepo.findAll();
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
        return supplierJpaRepo.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Supplier newSupplier) {
        return supplierJpaRepo.save(newSupplier);
    }

    @Override
    public void deleteSupplier(UUID id) {
        if (supplierJpaRepo.existsById(id)) {
            supplierJpaRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Supplier not found");
        }
    }
}
