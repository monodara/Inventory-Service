package com.inventorymanager.service.order;

import com.inventorymanager.domain.order.IOrderItemRepo;
import com.inventorymanager.domain.order.IOrderRepo;
import com.inventorymanager.domain.order.Order;
import com.inventorymanager.domain.order.OrderItem;
import com.inventorymanager.service.order.Dtos.OrderCreateDto;
import com.inventorymanager.service.order.Dtos.OrderItemCreateDto;
import com.inventorymanager.service.order.Dtos.OrderReadDto;
import com.inventorymanager.service.order.Dtos.OrderUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements IOrdeService{
    @Autowired
    private IOrderRepo orderRepo;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private IOrderItemRepo orderItemRepo;

    @Override
    public OrderReadDto getOrderById(UUID id) {
        Order order = orderRepo.getOrderById(id);
        return orderMapper.ReadOrder(order);
    }

    @Override
    public List<OrderReadDto> getAllOrders() {
        return orderRepo.getAllOrders().stream().map(orderMapper::ReadOrder).toList();
    }

    @Override
    public Order createOrder(OrderCreateDto orderCreateDto) {
        Order order = orderMapper.toOrder(orderCreateDto);
        Order OrderCreated = orderRepo.createOrder(order);

        for (OrderItemCreateDto itemDto : orderCreateDto.getOrderItems()) {
            OrderItem orderItem = orderItemMapper.toOrderItem(itemDto);
            orderItem.setOrder(OrderCreated);
            orderItemRepo.createOrderItem(orderItem);
        }
        return OrderCreated;
    }

    @Override
    public OrderReadDto updateOrder(UUID id, OrderUpdateDto newOrder) {
        return null;
    }

    @Override
    public void deleteOrder(UUID id) {

    }
}
