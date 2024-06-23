package com.inventorymanager.service.stock;

import com.inventorymanager.service.stock.Dtos.StockCreateDto;
import com.inventorymanager.service.stock.Dtos.StockReadDto;
import com.inventorymanager.service.stock.Dtos.StockUpdateDto;

import java.util.List;
import java.util.UUID;

public interface IStockService {
    public StockReadDto getStockById(UUID id);
    public List<StockReadDto> getAllStocks();
    public StockReadDto createStock(StockCreateDto stockCreateDto);
    public StockReadDto updateStock(UUID id, StockUpdateDto newStock);
    public void deleteStock(UUID id);
}
