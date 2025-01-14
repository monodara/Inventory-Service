package com.inventorymanager.infrastructure.stock;

import com.inventorymanager.domain.exception.ResourceNotFoundException;
import com.inventorymanager.domain.stock.IStockRepo;
import com.inventorymanager.domain.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StockRepo implements IStockRepo {
    @Autowired
    private IStockJpaRepo stockJpaRepo;
    @Override
    public Stock getStockById(UUID id) {
        return stockJpaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockJpaRepo.findAll();
    }

    @Override
    public Stock createStock(Stock stock) {
        return stockJpaRepo.save(stock);
    }

    @Override
    public Stock updateStock(Stock newStock) {
        return stockJpaRepo.save(newStock);
    }

    @Override
    public void deleteStock(UUID id) {
        stockJpaRepo.deleteById(id);
    }

    @Override
    public List<Stock> getStocksBySupplier(UUID supplierId) {
        return stockJpaRepo.findStocksBySupplierId(supplierId);
    }

    @Override
    public List<Stock> getStocksByProduct(String productId) {
        return stockJpaRepo.findStocksByProductId(productId);
    }

    @Override
    public List<Stock> findLowStockItems(int threshold){
        return stockJpaRepo.findLowStockItems(threshold);
    }
}
