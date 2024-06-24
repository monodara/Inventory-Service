package com.inventorymanager.service.order;

import com.inventorymanager.domain.exception.InsufficientStockException;
import com.inventorymanager.domain.order.IOrderItemRepo;
import com.inventorymanager.domain.order.IOrderRepo;
import com.inventorymanager.domain.order.Order;
import com.inventorymanager.domain.order.OrderItem;
import com.inventorymanager.domain.stock.IStockRepo;
import com.inventorymanager.domain.stock.Stock;
import com.inventorymanager.service.order.Dtos.OrderCreateDto;
import com.inventorymanager.service.order.Dtos.OrderItemCreateDto;
import com.inventorymanager.service.order.Dtos.OrderReadDto;
import com.inventorymanager.service.order.Dtos.OrderUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrdeService{
    @Autowired
    private IOrderRepo orderRepo;

    @Autowired
    private IStockRepo stockRepo;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

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
    @Transactional
    public OrderReadDto createOrder(OrderCreateDto orderCreateDto) {
        Order order = orderMapper.toOrder(orderCreateDto);
        List<OrderItem> orderItems = orderCreateDto.getOrderItems().stream()
                .map(itemDto -> {
                    Stock stock = stockRepo.getStockById(itemDto.getStockId());
                    if (stock.getQuantity() < itemDto.getQuantity()) {
                        throw new InsufficientStockException("Insufficient stock for item: " + stock.getId());
                    }
                    int orderedQuantity = itemDto.getQuantity();
                    int currentStockQuantity = stock.getQuantity();
                    int newStockQuantity = currentStockQuantity - orderedQuantity;
                    stock.setQuantity(newStockQuantity);
                    stockRepo.updateStock(stock);

                    OrderItem orderItem = orderItemMapper.toOrderItem(itemDto);
                    orderItem.setOrder(order); // Ensure order is set correctly
                    return orderItem;
                }).collect(Collectors.toList());
        order.setOrderItems(orderItems); // Set the orderItems to the order
        Order orderCreated = orderRepo.createOrder(order);
        return orderMapper.ReadOrder(orderCreated);
    }

    @Override
    public OrderReadDto updateOrder(UUID id, OrderUpdateDto newOrder) {
        return null;
    }

    @Override
    public void deleteOrder(UUID id) {

    }
}
