package com.cg.repository.bill;

import com.cg.domain.dto.service.billService.BillServiceDetailAccessoryResDTO;
import com.cg.domain.dto.service.billService.BillServiceDetailResDTO;
import com.cg.domain.entity.bill.BillServiceDetailAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IBillServiceDetailAccessoryRepository extends JpaRepository<BillServiceDetailAccessory, Long> {
    @Query("SELECT NEW com.cg.domain.dto.service.billService.BillServiceDetailAccessoryResDTO(" +
                "bsdas.id, " +
                "bsdas.accessoryCode, " +
                "bsdas.accessoryName, " +
                "bsdas.accessoryUnit, " +
                "bsdas.accessoryQuantity, " +
                "bsdas.accessoryPrice, " +
                "bsdas.amount, " +
                "bsdas.billServiceDetail " +
            ") " +
            "FROM BillServiceDetailAccessory as bsdas " +
            "WHERE bsdas.billServiceDetail.id = :billServiceDetailId "
    )
    List<BillServiceDetailAccessoryResDTO> findBillServiceDetailAccessoryResDTOByBillServiceDetailId(Long billServiceDetailId);
}
