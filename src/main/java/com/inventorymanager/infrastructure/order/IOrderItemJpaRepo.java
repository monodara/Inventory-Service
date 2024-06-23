package com.inventorymanager.infrastructure.order;

import com.inventorymanager.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderItemJpaRepo extends JpaRepository<OrderItem, UUID> {
}
