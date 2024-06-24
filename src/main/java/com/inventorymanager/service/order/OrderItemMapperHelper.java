package com.inventorymanager.service.order;

import com.inventorymanager.domain.stock.IStockRepo;
import com.inventorymanager.domain.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderItemMapperHelper {
    @Autowired
    private IStockRepo stockRepo;

    public Stock stockFromId(UUID stockId) {
        return stockRepo.getStockById(stockId);
    }
}
