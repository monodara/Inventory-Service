package com.inventorymanager.controller;

import com.inventorymanager.controller.shared.SuccessResponseEntity;
import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;
import com.inventorymanager.service.supplier.Dtos.SupplierUpdateDto;
import com.inventorymanager.service.supplier.ISupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/suppliers")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseEntity<SupplierReadDto>> getSupplierById(@PathVariable UUID id) {
        SupplierReadDto supplierReadDto = supplierService.getSupplierById(id);
        SuccessResponseEntity<SupplierReadDto> response = new SuccessResponseEntity<>();
        response.setData(new ArrayList<>(List.of(supplierReadDto)));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<SuccessResponseEntity<SupplierReadDto>> getAllSuppliers() {
        List<SupplierReadDto> suppliers = supplierService.getAllSuppliers();
        SuccessResponseEntity<SupplierReadDto> response = new SuccessResponseEntity<>();
        response.setData(suppliers);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SuccessResponseEntity<SupplierReadDto>> createSupplier(@RequestBody @Valid SupplierCreateDto supplierCreateDto) {
        SupplierReadDto supplierCreated = supplierService.createSupplier(supplierCreateDto);
        SuccessResponseEntity<SupplierReadDto> response = new SuccessResponseEntity<>();
        response.setData(new ArrayList<>(List.of(supplierCreated)));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponseEntity<SupplierReadDto>> updateSupplier(@PathVariable UUID id, @RequestBody SupplierUpdateDto supplierUpdateDto) {
        SupplierReadDto supplierUpdated = supplierService.updateSupplier(id, supplierUpdateDto);
        SuccessResponseEntity<SupplierReadDto> response = new SuccessResponseEntity<>();
        response.setData(new ArrayList<>(List.of(supplierUpdated)));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseEntity<Boolean>> deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
        SuccessResponseEntity<Boolean> response = new SuccessResponseEntity<>();
        response.setData(new ArrayList<>(List.of(true)));
        return ResponseEntity.ok(response);
    }
}
