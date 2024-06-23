package com.inventorymanager.service.stock;

import com.inventorymanager.domain.supplier.ISupplierRepo;
import com.inventorymanager.domain.supplier.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StockMapperHelper {
    @Autowired
    private ISupplierRepo supplierRepo;

    public Supplier supplierFromId(UUID supplierId) {
        return supplierRepo.getSupplierById(supplierId);
    }
}
