package com.inventorymanager.service.order;

import com.inventorymanager.service.order.Dtos.OrderCreateDto;
import com.inventorymanager.service.order.Dtos.OrderReadDto;
import com.inventorymanager.service.order.Dtos.OrderUpdateDto;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    public OrderReadDto getOrderById(UUID id);
    public List<OrderReadDto> getAllOrders();
    public OrderReadDto createOrder(OrderCreateDto orderCreateDto);
    public OrderReadDto updateOrder(UUID id, OrderUpdateDto newOrder);
    public boolean cancelOrder(UUID id);
    public void deleteOrder(UUID id);
}
