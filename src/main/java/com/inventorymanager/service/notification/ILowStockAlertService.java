package com.inventorymanager.service.notification;

import com.inventorymanager.domain.stock.Stock;

public interface ILowStockAlertService {
    public void sendLowStockAlert(String receiver, Stock stock);
}
