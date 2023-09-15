package com.cg.repository.bill;

import com.cg.domain.dto.service.billService.BillServiceResDTO;
import com.cg.domain.dto.service.billService.IBillServiceResDTO;
import com.cg.domain.entity.bill.BillService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBillServiceRepository extends JpaRepository<BillService, Long> {

    @Query("SELECT NEW com.cg.domain.dto.service.billService.BillServiceResDTO(" +
                "bs.id, " +
                "bs.totalAmount, " +
                "bs.amountTax, " +
                "bs.totalAmountAfterTax, " +
                "bs.createdAt, " +
                "bs.orderService, " +
                "mcp.car " +
            ") " +
            "FROM BillService as bs " +
            "JOIN ManagementCarPlate as mcp " +
            "ON mcp.carNumberPlate = bs.orderService.carQueue.carNumberPlates "
    )
    List<BillServiceResDTO> fillAllBillServiceResDTO();

    @Query("SELECT NEW com.cg.domain.dto.service.billService.BillServiceResDTO(" +
                "bs.id, " +
                "bs.totalAmount, " +
                "bs.amountTax, " +
                "bs.totalAmountAfterTax, " +
                "bs.createdAt, " +
                "bs.orderService, " +
                "mcp.car " +
            ") " +
            "FROM BillService as bs " +
            "JOIN ManagementCarPlate as mcp " +
            "ON mcp.carNumberPlate = bs.orderService.carQueue.carNumberPlates " +
            "WHERE bs.id = :id "
    )
    Optional<BillServiceResDTO> findBillServiceResDTOById(@Param("id") Long id);

    @Query("SELECT NEW com.cg.domain.dto.service.billService.BillServiceResDTO(" +
                "bs.id, " +
                "bs.totalAmount, " +
                "bs.amountTax, " +
                "bs.totalAmountAfterTax, " +
                "bs.createdAt, " +
                "bs.orderService, " +
                "mcp.car " +
            ") " +
            "FROM BillService as bs " +
            "JOIN ManagementCarPlate as mcp " +
            "ON mcp.carNumberPlate = bs.orderService.carQueue.carNumberPlates " +
            "WHERE bs.createdAt = :dayRevenue "
    )
    List<BillServiceResDTO> findBillServiceResDTOByDay(@Param("dayRevenue") LocalDateTime dayRevenue);

    @Query("SELECT NEW com.cg.domain.dto.service.billService.BillServiceResDTO(" +
                "bs.id, " +
                "bs.totalAmount, " +
                "bs.amountTax, " +
                "bs.totalAmountAfterTax, " +
                "bs.createdAt, " +
                "bs.orderService, " +
                "mcp.car " +
            ") " +
            "FROM BillService as bs " +
            "JOIN ManagementCarPlate as mcp " +
            "ON mcp.carNumberPlate = bs.orderService.carQueue.carNumberPlates " +
            "WHERE bs.createdAt between :startDateTime and :endDateTime " +
            "ORDER BY bs.createdAt"
    )
    List<BillServiceResDTO> findAllBillServiceResDTOByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query(value = "" +
            "SELECT " +
                "bs.id AS id, " +
                "bs.total_amount_after_tax AS totalAmountAfterTax, " +
                "bs.created_at AS createdAt, " +
            "cq.car_number_plates AS carPlate " +
            "FROM bill_service AS bs " +
            "JOIN order_service AS os " +
            "ON os.id = bs.order_service_id " +
            "JOIN car_queue AS cq " +
            "ON cq.id = os.car_queue_id " +
            "ORDER BY bs.created_at DESC " +
            "LIMIT 5" ,
            nativeQuery = true
    )
    List<IBillServiceResDTO> fillAllBillServiceTopFiveResDTO();
}
