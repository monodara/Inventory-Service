package com.inventorymanager.service.supplier;

import com.inventorymanager.domain.supplier.ISupplierRepo;
import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;
import com.inventorymanager.service.supplier.Dtos.SupplierUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class SupplierService implements ISupplierService{
    @Autowired
    private ISupplierRepo supplierRepo;
    @Autowired
    private SupplierMapper supplierMapper;

    public void createDummySuppliers() {
        IntStream.range(0, 20).forEach(i -> {
            Supplier supplier = new Supplier();
            supplier.setId(UUID.randomUUID());
            supplier.setName("Supplier " + i);
            supplier.setAddress("Address " + i);
            supplier.setEmail("supplier" + i + "@example.com");
            supplier.setPhone("123456789" + i);
            supplier.setPassword("password" + i);
            supplierRepo.createSupplier(supplier);
        });
    }
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
    public SupplierReadDto createSupplier(SupplierCreateDto supplierCreateDto) {
        Supplier supplier = supplierMapper.toSupplier(supplierCreateDto);
        Supplier supplierCreated = supplierRepo.createSupplier(supplier);
        return supplierMapper.toReadDto(supplierCreated);
    }

    @Override
    public SupplierReadDto updateSupplier(UUID id, SupplierUpdateDto supplierUpdateDto) {
        Supplier supplier = supplierRepo.getSupplierById(id);
        supplierMapper.updateSupplierFromDto(supplierUpdateDto, supplier);
        Supplier supplierUpdated = supplierRepo.updateSupplier(supplier);
        return supplierMapper.toReadDto(supplierUpdated);
    }

    @Override
    public void deleteSupplier(UUID id) {
        supplierRepo.deleteSupplier(id);
    }
}
