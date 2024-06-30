package com.inventorymanager.scheduler;

import com.inventorymanager.domain.stock.IStockRepo;
import com.inventorymanager.domain.stock.Stock;
import com.inventorymanager.service.notification.IEmaiService;
import com.inventorymanager.service.notification.ILowStockAlertService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StockCheckSchedule {
    @Autowired
    private IStockRepo stockRepo;
    @Autowired
    private ILowStockAlertService lowStockAlertService;

    @Value("${stock.threshold}")
    private int threshold;

    @Scheduled(cron = "0 0 8 * * ?") // every day at 8am
    public void checkStockLevels() {
        List<Stock> lowStockItems = stockRepo.findLowStockItems(threshold);

        // Group by supplier
        Map<String, List<Stock>> groupedBySupplier = lowStockItems.stream()
                .collect(Collectors.groupingBy(stock -> stock.getSupplier().getEmail()));

        // Send email to suppliers
        groupedBySupplier.forEach((supplierEmail, stocks) -> {
            lowStockAlertService.sendLowStockAlert(supplierEmail, stocks);
        });
    }
}
