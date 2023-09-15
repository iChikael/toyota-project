package com.cg.repository.storage;

import com.cg.domain.entity.storage.SupplierOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierOrderDetailRepository extends JpaRepository<SupplierOrderDetail, Long> {
}
