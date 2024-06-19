package com.inventorymanager.controller;

import com.inventorymanager.domain.supplier.Supplier;
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

    @GetMapping("/id")
    public Supplier getSupplierById(@PathVariable UUID id) {
        return supplierService.getSupplierById(id);
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }


    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierService.createSupplier(supplier);
    }

    @PatchMapping("/id")
    public Supplier updateSupplier(@PathVariable UUID id, @RequestBody Supplier newSupplier) {
        return supplierService.updateSupplier(id, newSupplier);
    }

    @DeleteMapping("/id")
    public void deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
    }
}