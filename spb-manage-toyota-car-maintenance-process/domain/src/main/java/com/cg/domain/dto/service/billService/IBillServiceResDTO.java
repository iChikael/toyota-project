package com.cg.domain.dto.service.billService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IBillServiceResDTO {

    Long getId();
    BigDecimal getTotalAmountAfterTax();
    LocalDateTime getCreatedAt();
    String getCarPlate();
}
