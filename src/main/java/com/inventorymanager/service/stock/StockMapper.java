package com.inventorymanager.service.stock;

import com.inventorymanager.domain.stock.Stock;
import com.inventorymanager.service.stock.Dtos.StockCreateDto;
import com.inventorymanager.service.stock.Dtos.StockReadDto;
import com.inventorymanager.service.stock.Dtos.StockUpdateDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = StockMapperHelper.class)
public interface StockMapper {

    @Mapping(target = "supplier", source = "supplierId")
    Stock toStock(StockCreateDto stockCreateDto);

    @Mapping(target = "supplier", source = "supplier")
    StockReadDto readStock(Stock stock);

    @Mapping(target = "supplier", source = "supplierId")
    void updateStockFromDto(StockUpdateDto updateDto, @MappingTarget Stock stock);
}
