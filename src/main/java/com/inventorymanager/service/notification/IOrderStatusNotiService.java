package com.inventorymanager.service.notification;

import com.inventorymanager.domain.order.Order;

public interface IOrderStatusNotiService {
    public void sendShippedNotification(Order order);
}
