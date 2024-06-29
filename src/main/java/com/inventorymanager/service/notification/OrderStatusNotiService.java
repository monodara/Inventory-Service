package com.inventorymanager.service.notification;

import com.inventorymanager.domain.order.Order;
import com.inventorymanager.domain.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusNotiService implements IOrderStatusNotiService{
    @Autowired
    private IEmaiService emaiService;
    @Override
    public void sendShippedNotification(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        StringBuilder htmlContentBuilder = new StringBuilder();

        htmlContentBuilder.append("<h3>This is a message from PeacoPlaza</h3>")
                .append("<p>Your order has been shipped. Below is the order information:</p>")
                .append("<p>Order ID: ").append(order.getId()).append("</p>")
                .append("<table border='1' style='border-collapse: collapse; width: 50%;'>")
                .append("<tr>")
                .append("<th style='padding: 8px; text-align: left;'>Product Name</th>")
                .append("<th style='padding: 8px; text-align: left;'>Quantity</th>")
                .append("</tr>");

        for (OrderItem item : orderItems) {
            htmlContentBuilder.append("<tr>")
                    .append("<td style='padding: 8px;'>").append(item.getStock().getProductId()).append("</td>")
                    .append("<td style='padding: 8px;'>").append(item.getQuantity()).append("</td>")
                    .append("</tr>");
        }

        htmlContentBuilder.append("</table>");

        String htmlContent = htmlContentBuilder.toString();
        emaiService.sendHtmlEmail(order.getClientEmail(), "Your order has been shipped.", htmlContent);
    }
}
