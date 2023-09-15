package com.cg.repository.order;


import com.cg.domain.dto.service.orderService.orderService.IOrderServiceCurrentServiceAreaResDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderServiceCurrentServiceAreaResDTO;
import com.cg.domain.entity.orderService.OrderServiceCurrentServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderServiceCurrentServiceAreaRepository extends JpaRepository<OrderServiceCurrentServiceArea, Long> {

    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderService.OrderServiceCurrentServiceAreaResDTO(" +
                "oscsa.id, " +
                "oscsa.orderService, " +
                "oscsa.createdAt, " +
                "oscsa.serviceAreaName " +
            ") " +
            "FROM OrderServiceCurrentServiceArea AS oscsa " +
            "WHERE oscsa.orderService.id = :orderServiceId " +
            "AND oscsa.deleted = false"
    )
    Optional<OrderServiceCurrentServiceAreaResDTO> findResDTOByOrderServiceIdAndDeletedIsFalse (@Param("orderServiceId") Long orderServiceId);

    @Query(value = "" +
            "SELECT "+
                "oscsa.id, " +
                "oscsa.created_at AS createdAt, " +
                "oscsa.service_area_name AS serviceAreaName " +
            "FROM order_service_current_service_areas AS oscsa " +
            "WHERE oscsa.order_service_id = :orderServiceId " +
            "ORDER BY oscsa.created_at ASC " +
            "LIMIT 2",
            nativeQuery = true
    )
    List<IOrderServiceCurrentServiceAreaResDTO> findByOrderServiceIdAndTimeCreate (@Param("orderServiceId") Long orderServiceId);

    @Query(value = "" +
            "SELECT " +
                "oscsa.id, " +
                "oscsa.created_at AS createdAt, " +
                "oscsa.service_area_name as serviceAreaName " +
            "FROM order_service_current_service_areas AS oscsa " +
            "WHERE oscsa.order_service_id = :orderServiceId " +
            "ORDER BY oscsa.created_at DESC " +
            "LIMIT 1",
            nativeQuery = true
    )
    Optional<IOrderServiceCurrentServiceAreaResDTO> findByOrderServiceIdAndTimeDone (@Param("orderServiceId") Long orderServiceId);

    Optional<OrderServiceCurrentServiceArea> findByOrderServiceIdAndDeletedIsFalse (Long orderServiceId);
}
