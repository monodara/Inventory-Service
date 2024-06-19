package com.inventorymanager.service.supplier;

import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;
import com.inventorymanager.service.supplier.Dtos.SupplierUpdateDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface SupplierMapper {
    Supplier toSupplier(SupplierCreateDto supplierCreateDto);
    SupplierReadDto toReadDto(Supplier supplier);
    void updateSupplierFromDto(SupplierUpdateDto dto, @MappingTarget Supplier entity);
}
