package com.inventorymanager.service.stock;

import com.inventorymanager.domain.stock.Stock;
import com.inventorymanager.service.stock.Dtos.StockCreateDto;
import com.inventorymanager.service.stock.Dtos.StockReadDto;
import com.inventorymanager.service.stock.Dtos.StockUpdateDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface StockMapper {
    Stock toStock(StockCreateDto stockCreateDto);
    StockReadDto readStock(Stock stock);
    void updateStockFromDto(StockUpdateDto updateDto, @MappingTarget Stock stock);
}
