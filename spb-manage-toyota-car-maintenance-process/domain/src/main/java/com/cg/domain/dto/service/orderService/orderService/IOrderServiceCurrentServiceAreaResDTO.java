package com.cg.domain.dto.service.orderService.orderService;

import java.time.LocalDateTime;

public interface IOrderServiceCurrentServiceAreaResDTO {
    Long getId();
    LocalDateTime getCreatedAt();
    String getServiceAreaName();
}
