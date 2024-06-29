package com.inventorymanager.service.notification;

import com.inventorymanager.domain.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LowStockAlertService implements ILowStockAlertService{
    @Autowired
    private IEmaiService emaiService;
    private final String subject = "!Alert: Low Stock Level!";
    @Override
    public void sendLowStockAlert(String receiver, Stock stock) {
        String htmlContent = "<h1>This is a reminder from PeacoPlaza</h1>" +
                "<p>The stock level is below " + Constants.STOCK_THRESHOLD + ". Below is the stock information:</p>" +
                "<table border='1' style='border-collapse: collapse; width: 50%;'>" +
                "<tr>" +
                "<th style='padding: 8px; text-align: left;'>Stock ID</th>" +
                "<th style='padding: 8px; text-align: left;'>Product ID</th>" +
                "<th style='padding: 8px; text-align: left;'>Quantity</th>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 8px;'>" + stock.getId() + "</td>" +
                "<td style='padding: 8px;'>" + stock.getProductId() + "</td>" +
                "<td style='padding: 8px;'>" + stock.getQuantity() + "</td>" +
                "</tr>" +
                "</table>";
        emaiService.sendHtmlEmail(receiver, subject, htmlContent);
    }
}
