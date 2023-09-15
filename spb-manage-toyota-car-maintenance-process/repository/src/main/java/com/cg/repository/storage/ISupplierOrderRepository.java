package com.cg.repository.storage;

import com.cg.domain.entity.storage.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierOrderRepository extends JpaRepository<SupplierOrder, Long> {
}
