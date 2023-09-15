package com.cg.repository.storage;

import com.cg.domain.entity.storage.DeliveryBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeliveryBillRepository extends JpaRepository<DeliveryBill, Long> {
}
