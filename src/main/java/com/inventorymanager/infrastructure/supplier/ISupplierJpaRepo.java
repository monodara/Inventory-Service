package com.inventorymanager.infrastructure.supplier;

import com.inventorymanager.domain.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ISupplierJpaRepo extends JpaRepository<Supplier, UUID> {
    Optional<Supplier> findSupplierByName(String name);
}
