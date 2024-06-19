package com.inventorymanager.infrastructure;

import com.inventorymanager.domain.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ISupplierJpaRepo extends JpaRepository<Supplier, UUID> {
}
