package com.inventorymanager.service.notification;

import com.inventorymanager.domain.stock.Stock;

public interface IEmaiService {
    public void sendHtmlEmail(String receiver, String subject, String htmlContent);
}
