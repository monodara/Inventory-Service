package com.inventorymanager.infrastructure.order;

import com.inventorymanager.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderJpaRepo extends JpaRepository<Order, UUID> {
}
