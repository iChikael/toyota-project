package com.cg.repository.order;

import com.cg.domain.dto.service.orderService.orderService.OrderServiceResDTO;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.enums.EStatusCarQueue;
import com.cg.domain.enums.EStatusOrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderServiceRepository extends JpaRepository<OrderService, Long> {
    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderService.OrderServiceResDTO(" +
                "os.id, " +
                "os.totalAmount, " +
                "os.distance, " +
                "os.customerReq, " +
                "os.doEarly, " +
                "os.tax, " +
                "os.amountTax, " +
                "os.totalAmountAfterTax, " +
                "os.status, " +
                "oscsa.serviceAreaName, " +
                "os.carQueue, " +
                "mcp.car " +
            ") " +
            "FROM OrderService AS os " +
            "JOIN ManagementCarPlate AS mcp " +
            "ON mcp.carNumberPlate = os.carQueue.carNumberPlates " +
            "JOIN OrderServiceCurrentServiceArea AS oscsa " +
            "ON oscsa.orderService.id = os.id " +
            "AND oscsa.deleted = false " +
            "WHERE os.status = :status"
    )
    List<OrderServiceResDTO> findAllOrderServiceByStatusResDTO(@Param("status") EStatusOrderService status);

    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderService.OrderServiceResDTO(" +
                "os.id, " +
                "os.totalAmount, " +
                "os.distance, " +
                "os.customerReq, " +
                "os.doEarly, " +
                "os.tax, " +
                "os.amountTax, " +
                "os.totalAmountAfterTax, " +
                "os.status, " +
                "oscsa.serviceAreaName, " +
                "os.carQueue, " +
                "mcp.car " +
            ") " +
            "FROM OrderService AS os " +
            "JOIN ManagementCarPlate AS mcp " +
            "ON mcp.carNumberPlate = os.carQueue.carNumberPlates " +
            "JOIN OrderServiceCurrentServiceArea AS oscsa " +
            "ON oscsa.orderService.id = os.id " +
            "AND oscsa.deleted = false " +
            "WHERE os.status = :status " +
            "AND os.id = :id"
    )
    Optional<OrderServiceResDTO> findOrderServiceResDTOByIdAndStatus (@Param("id") Long id, @Param("status") EStatusOrderService status);

    Optional<OrderService> findByCarQueueAndDeletedIsFalse(CarQueue carQueue);

    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderService.OrderServiceResDTO(" +
                "oscsa.orderService.id, " +
                "oscsa.orderService.totalAmount, " +
                "oscsa.orderService.distance, " +
                "oscsa.orderService.customerReq, " +
                "oscsa.orderService.doEarly, " +
                "oscsa.orderService.tax, " +
                "oscsa.orderService.amountTax, " +
                "oscsa.orderService.totalAmountAfterTax, " +
                "oscsa.orderService.status, " +
                "oscsa.serviceAreaName, " +
                "oscsa.orderService.carQueue, " +
                "mcp.car " +
            ") " +
            "FROM OrderServiceCurrentServiceArea AS oscsa " +
            "JOIN ManagementCarPlate AS mcp " +
            "ON mcp.carNumberPlate = oscsa.orderService.carQueue.carNumberPlates " +
            "WHERE oscsa.serviceAreaName = :currentServiceArea " +
            "AND oscsa.deleted = false"
    )
    List<OrderServiceResDTO> findAllOrderServiceByServiceAreaNameAndDeletedIsFalseResDTO (@Param("currentServiceArea") String currentServiceArea);

    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderService.OrderServiceResDTO(" +
                "os.id, " +
                "os.totalAmount, " +
                "os.distance, " +
                "os.customerReq, " +
                "os.doEarly, " +
                "os.tax, " +
                "os.amountTax, " +
                "os.totalAmountAfterTax, " +
                "os.status, " +
                "oscsa.serviceAreaName, " +
                "os.carQueue, " +
                "mcp.car " +
            ") " +
            "FROM OrderService AS os " +
            "JOIN ManagementCarPlate AS mcp " +
            "ON mcp.carNumberPlate = os.carQueue.carNumberPlates " +
            "JOIN OrderServiceCurrentServiceArea AS oscsa " +
            "ON oscsa.orderService.id = os.id " +
            "AND oscsa.deleted = false " +
            "WHERE os.carQueue.status = :eStatusCarQueue " +
            "AND os.deleted = false"
    )
    List<OrderServiceResDTO> findAllByStatusCarQueueDoneAndDeletedIsFalse (EStatusCarQueue eStatusCarQueue);

    Optional<OrderService> findOrderServiceByIdAndStatus (Long id, EStatusOrderService eStatusOrderService);

    Optional<OrderService> findByIdAndDeletedIsFalse(Long id);

    Optional<OrderService> findOrderServiceByCarQueueAndDeletedIsFalse (CarQueue carQueue);
}
