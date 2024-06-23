package com.inventorymanager.service.stock;

import com.inventorymanager.domain.stock.Stock;
import com.inventorymanager.service.stock.Dtos.StockCreateDto;
import com.inventorymanager.service.stock.Dtos.StockReadDto;
import com.inventorymanager.service.stock.Dtos.StockUpdateDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
//public interface StockMapper {
//    Stock toStock(StockCreateDto stockCreateDto);
//    StockReadDto readStock(Stock stock);
//    void updateStockFromDto(StockUpdateDto updateDto, @MappingTarget Stock stock);
//}
@Mapper(componentModel = "spring", uses = StockMapperHelper.class)
public interface StockMapper {

    @Mapping(target = "supplier", source = "supplierId")
    Stock toStock(StockCreateDto stockCreateDto);

//    @Mapping(target = "supplierId", source = "supplier.id")
//    StockCreateDto toStockCreateDto(Stock stock);
    @Mapping(target = "supplier", source = "supplier")
    StockReadDto readStock(Stock stock);

    void updateStockFromDto(StockUpdateDto updateDto, @MappingTarget Stock stock);
}
