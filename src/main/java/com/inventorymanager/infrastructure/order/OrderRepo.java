package com.inventorymanager.infrastructure.order;

import com.inventorymanager.domain.exception.ResourceNotFoundException;
import com.inventorymanager.domain.order.IOrderRepo;
import com.inventorymanager.domain.order.Order;
import com.inventorymanager.domain.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepo implements IOrderRepo {
    @Autowired
    private IOrderJpaRepo orderJpaRepo;
    @Autowired
    private IOrderItemJpaRepo orderItemJpaRepo;
    @Override
    public Order getOrderById(UUID id) {
        return orderJpaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderJpaRepo.findAll();
    }

    @Override
    public Order createOrder(Order order) {
        return orderJpaRepo.save(order);
    }

    @Override
    public Order updateOrder(Order newOrder) {
        System.out.println(newOrder.getStatus());
        return orderJpaRepo.save(newOrder);
    }

    @Override
    public void deleteOrder(UUID id) {
        orderJpaRepo.deleteById(id);
    }

    @Override
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderJpaRepo.findByOrderDateBetween(startDate,endDate);
    }
}
