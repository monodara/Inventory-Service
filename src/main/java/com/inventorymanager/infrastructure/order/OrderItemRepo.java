package com.inventorymanager.infrastructure.order;

import com.inventorymanager.domain.order.IOrderItemRepo;
import com.inventorymanager.domain.order.IOrderRepo;
import com.inventorymanager.domain.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public class OrderItemRepo implements IOrderItemRepo {
    @Autowired
    private IOrderItemJpaRepo orderItemJpaRepo;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemJpaRepo.save(orderItem);
    }
}
