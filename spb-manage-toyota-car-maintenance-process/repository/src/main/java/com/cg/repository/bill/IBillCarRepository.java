package com.cg.repository.bill;

import com.cg.domain.entity.bill.BillCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillCarRepository extends JpaRepository<BillCar, Long> {
}
