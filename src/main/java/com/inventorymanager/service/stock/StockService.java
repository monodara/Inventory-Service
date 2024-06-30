package com.inventorymanager.service.stock;

import com.inventorymanager.domain.stock.IStockRepo;
import com.inventorymanager.domain.stock.Stock;
import com.inventorymanager.domain.supplier.ISupplierRepo;
import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.stock.Dtos.StockCreateDto;
import com.inventorymanager.service.stock.Dtos.StockReadDto;
import com.inventorymanager.service.stock.Dtos.StockUpdateDto;
import com.inventorymanager.service.stock.IStockService;
import com.inventorymanager.service.supplier.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class StockService implements IStockService {
    @Autowired
    private IStockRepo stockRepo;
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private ISupplierRepo supplierRepo;

    public void createDummyStocks() {
        List<Supplier> suppliers = supplierRepo.getAllSuppliers();
        IntStream.range(0, 20).forEach(i -> {
            Stock stock = new Stock();
            stock.setId(UUID.randomUUID());
            stock.setQuantity((int) (Math.random() * 100));
            stock.setProductId("Product " + i);
            stock.setInputPrice(Math.random() * 100);
            stock.setSupplier(suppliers.get(i % suppliers.size()));
            stockRepo.createStock(stock);
        });
    }
    @Override
    public StockReadDto getStockById(UUID id) {
        Stock stock = stockRepo.getStockById(id);
        return stockMapper.readStock(stock);
    }

    @Override
    public List<StockReadDto> getAllStocks() {
        List<Stock> stocks = stockRepo.getAllStocks();
        return stocks.stream().map(stock -> stockMapper.readStock(stock)).toList();
    }

    @Override
    public StockReadDto createStock(StockCreateDto stockCreateDto) {
        Stock stock = stockMapper.toStock(stockCreateDto);
        Stock stockAdded = stockRepo.createStock(stock);
        return stockMapper.readStock(stockAdded);
    }

    @Override
    public StockReadDto updateStock(UUID id, StockUpdateDto stockUpdateDto) {
        Stock stock = stockRepo.getStockById(id);
        stockMapper.updateStockFromDto(stockUpdateDto, stock);
        Stock stockUpdated = stockRepo.updateStock(stock);
        return stockMapper.readStock(stockUpdated);
    }

    @Override
    public void deleteStock(UUID id) {
        stockRepo.deleteStock(id);
    }

    @Override
    public List<StockReadDto> getStocksBySupplier(UUID supplierId) {
        return stockRepo.getStocksBySupplier(supplierId).stream().map(stockMapper::readStock).toList();
    }

    @Override
    public List<StockReadDto> getStocksByProduct(String productId) {
        return stockRepo.getStocksByProduct(productId).stream().map(stockMapper::readStock).toList();
    }
}
