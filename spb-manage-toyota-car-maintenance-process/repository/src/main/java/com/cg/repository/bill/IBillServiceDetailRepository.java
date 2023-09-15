package com.cg.repository.bill;

import com.cg.domain.dto.service.billService.BillServiceDetailResDTO;
import com.cg.domain.entity.bill.BillServiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IBillServiceDetailRepository extends JpaRepository<BillServiceDetail, Long> {
    @Query("SELECT NEW com.cg.domain.dto.service.billService.BillServiceDetailResDTO(" +
                "bsd.id, " +
                "bsd.serviceName, " +
                "bsd.packName, " +
                "bsd.payment, " +
                "bsd.unitWage, " +
                "bsd.quantity, " +
                "bsd.priceWage, " +
                "bsd.fees, " +
                "bsd.discount, " +
                "bsd.amount, " +
                "bsd.billService " +
            ") " +
            "FROM BillServiceDetail as bsd " +
            "WHERE bsd.billService.id = :billServiceId "
    )
    List<BillServiceDetailResDTO> findAllBillServiceDetailResDTOByBillServiceId(Long billServiceId);
}
