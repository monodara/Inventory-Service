package com.inventorymanager.service.supplier;

import com.inventorymanager.domain.supplier.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierService implements ISupplierService{
    @Override
    public Supplier getSupplierById(UUID id) {
        return null;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return null;
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
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
