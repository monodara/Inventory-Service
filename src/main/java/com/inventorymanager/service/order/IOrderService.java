package com.inventorymanager.service.order;

import com.inventorymanager.domain.order.DailySalesReport;
import com.inventorymanager.domain.order.Order;
import com.inventorymanager.service.order.Dtos.OrderCreateDto;
import com.inventorymanager.service.order.Dtos.OrderReadDto;
import com.inventorymanager.service.order.Dtos.OrderUpdateDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IOrderService {
    public OrderReadDto getOrderById(UUID id);
    public List<OrderReadDto> getAllOrders();
    public OrderReadDto createOrder(OrderCreateDto orderCreateDto);
    public OrderReadDto updateOrder(UUID id, OrderUpdateDto newOrder);
    public boolean cancelOrder(UUID id);
    public void deleteOrder(UUID id);

    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    Map<LocalDate, DailySalesReport> getDailySalesReport(LocalDateTime startDate, LocalDateTime endDate);
}
