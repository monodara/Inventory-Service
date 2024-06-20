package com.inventorymanager.infrastructure.stock;

import com.inventorymanager.domain.supplier.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IStockJpaRepo extends JpaRepository<Stock, UUID> {
}
