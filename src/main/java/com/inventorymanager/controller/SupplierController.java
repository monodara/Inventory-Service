package com.inventorymanager.controller;

import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;
import com.inventorymanager.service.supplier.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/suppliers")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    @GetMapping("/{id}")
    public SupplierReadDto getSupplierById(@PathVariable UUID id) {
        System.out.println(id);
        return supplierService.getSupplierById(id);
    }

    @GetMapping
    public List<SupplierReadDto> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }


    @PostMapping
    public Supplier createSupplier(@RequestBody SupplierCreateDto supplierCreateDto) {
        return supplierService.createSupplier(supplierCreateDto);
    }

    @PatchMapping("/{id}")
    public Supplier updateSupplier(@PathVariable UUID id, @RequestBody Supplier newSupplier) {
        return supplierService.updateSupplier(id, newSupplier);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
    }
}
