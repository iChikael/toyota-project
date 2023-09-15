package com.cg.repository.bill;

import com.cg.domain.entity.bill.BillCarDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillCarDetailRepository extends JpaRepository<BillCarDetail, Long> {
}
