package com.inventorymanager.service.supplier;

import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface SupplierMapper {
    Supplier toSupplier(SupplierCreateDto supplierCreateDto);
}
