package com.inventorymanager.service.supplier;

import com.inventorymanager.domain.supplier.ISupplierRepo;
import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierService implements ISupplierService{
    @Autowired
    private ISupplierRepo supplierRepo;
    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public Supplier getSupplierById(UUID id) {
        return supplierRepo.getSupplierById(id);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepo.getAllSuppliers();
    }

    @Override
    public Supplier createSupplier(SupplierCreateDto supplierCreateDto) {
        Supplier supplier = supplierMapper.toSupplier(supplierCreateDto);
        return supplierRepo.createSupplier(supplier);
    }

    @Override
    public Supplier updateSupplier(UUID id, Supplier newSupplier) {
        return supplierRepo.updateProduct(id, newSupplier);
    }

    @Override
    public void deleteSupplier(UUID id) {
        supplierRepo.deleteSupplier(id);
    }
}
