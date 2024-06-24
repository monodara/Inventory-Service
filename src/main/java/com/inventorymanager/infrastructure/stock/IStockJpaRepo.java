package com.inventorymanager.infrastructure.stock;

import com.inventorymanager.domain.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IStockJpaRepo extends JpaRepository<Stock, UUID> {
    @Query("SELECT s FROM Stock s WHERE s.supplier.id = :supplierId")
    List<Stock> findStocksBySupplierId(@Param("supplierId") UUID supplierId);
    @Query("SELECT s FROM Stock s WHERE s.productId = :productId")
    List<Stock> findStocksByProductId(String productId);
}
