package com.inventorymanager.service.notification;

import com.inventorymanager.domain.stock.Stock;

import java.util.List;

public interface ILowStockAlertService {
    public void sendLowStockAlert(String receiver, List<Stock> stocks);
}
