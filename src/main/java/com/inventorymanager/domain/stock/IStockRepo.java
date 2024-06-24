package com.inventorymanager.domain.stock;

import com.inventorymanager.domain.stock.Stock;
import com.inventorymanager.service.stock.Dtos.StockReadDto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface IStockRepo {
    public Stock getStockById(UUID id);
    public List<Stock> getAllStocks();
    public Stock createStock(Stock stock);
    public Stock updateStock(Stock newStock);
    public void deleteStock(UUID id);

    public List<Stock> getStocksBySupplier(UUID supplierId);

    public List<Stock> getStocksByProduct(String productId);
}
