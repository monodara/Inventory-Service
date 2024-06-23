package com.inventorymanager.domain.order;

import com.inventorymanager.domain.order.Order;

import java.util.List;
import java.util.UUID;

public interface IOrderRepo {
    public Order getOrderById(UUID id);
    public List<Order> getAllOrders();
    public Order createOrder(Order order);
    public Order updateOrder(Order newOrder);
    public void deleteOrder(UUID id);
}
