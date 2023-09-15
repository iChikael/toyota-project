package com.cg.repository.storage;

import com.cg.domain.entity.storage.DeliveryBillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeliveryBillDetailRepository extends JpaRepository<DeliveryBillDetail, Long> {
}
