package com.inventorymanager.domain.stock;

import com.inventorymanager.domain.stock.Stock;

import java.util.List;
import java.util.UUID;

public interface IStockRepo {
    public Stock getStockById(UUID id);
    public List<Stock> getAllStocks();
    public Stock createStock(Stock stock);
    public Stock updateStock(Stock newStock);
    public void deleteStock(UUID id);
}
