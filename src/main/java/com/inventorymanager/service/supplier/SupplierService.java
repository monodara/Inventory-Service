package com.inventorymanager.service.supplier;

import com.inventorymanager.domain.supplier.ISupplierRepo;
import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;
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
    public SupplierReadDto getSupplierById(UUID id) {
        Supplier supplier = supplierRepo.getSupplierById(id);
        return supplierMapper.toReadDto(supplier);
    }

    @Override
    public List<SupplierReadDto> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepo.getAllSuppliers();
        return suppliers.stream().map(s -> supplierMapper.toReadDto(s)).toList();
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
