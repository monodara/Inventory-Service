package com.inventorymanager.service.notification;

import com.inventorymanager.domain.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LowStockAlertService implements ILowStockAlertService{
    @Autowired
    private IEmaiService emaiService;
    @Value("${stock.threshold}")
    private int threshold;
    private final String subject = "!Alert: Low Stock Level!";
    @Override
    public void sendLowStockAlert(String receiver, List<Stock> stocks) {
        StringBuilder htmlContent = new StringBuilder("<h1>Low Stock Alert</h1>");
        htmlContent.append("<p>The following items are below the threshold of ").append(threshold).append(" units:</p>");
        htmlContent.append("<table border='1' style='border-collapse: collapse; width: 100%;'>")
                .append("<tr>")
                .append("<th>Stock ID</th>")
                .append("<th>Product ID</th>")
                .append("<th>Quantity</th>")
                .append("</tr>");

        for (Stock stock : stocks) {
            htmlContent.append("<tr>")
                    .append("<td>").append(stock.getId()).append("</td>")
                    .append("<td>").append(stock.getProductId()).append("</td>")
                    .append("<td>").append(stock.getQuantity()).append("</td>")
                    .append("</tr>");
        }

        htmlContent.append("</table>");
        emaiService.sendHtmlEmail(receiver, subject, htmlContent.toString());
    }
}
