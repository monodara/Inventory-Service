package com.inventorymanager;

import com.inventorymanager.service.order.OrderService;
import com.inventorymanager.service.stock.StockService;
import com.inventorymanager.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private StockService stockService;

    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        supplierService.createDummySuppliers();
        stockService.createDummyStocks();
        orderService.createDummyOrders();
    }
}
