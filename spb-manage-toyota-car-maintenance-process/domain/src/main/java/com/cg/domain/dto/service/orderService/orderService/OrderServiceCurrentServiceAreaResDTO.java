package com.cg.domain.dto.service.orderService.orderService;

import com.cg.domain.entity.orderService.OrderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderServiceCurrentServiceAreaResDTO {
    private Long id;
    private OrderServiceResDTO orderService;
    private LocalDateTime createdAt;
    private String serviceAreaName;
    public OrderServiceCurrentServiceAreaResDTO(Long id, OrderService orderService, LocalDateTime createdAt, String serviceAreaName) {
        this.id = id;
        this.orderService = orderService.toOrderServiceResDTO();
        this.createdAt = createdAt;
        this.serviceAreaName = serviceAreaName;
    }
}
