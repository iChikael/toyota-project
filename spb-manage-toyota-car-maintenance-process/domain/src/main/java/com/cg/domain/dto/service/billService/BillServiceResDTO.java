package com.cg.domain.dto.service.billService;

import com.cg.domain.dto.car.CarResDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderServiceResDTO;
import com.cg.domain.entity.car.Car;
import com.cg.domain.entity.orderService.OrderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillServiceResDTO {
    private Long id;
    private BigDecimal totalAmount;
    private BigDecimal amountTax;
    private BigDecimal totalAmountAfterTax;
    private LocalDateTime createdAt;
    private OrderServiceResDTO orderService;
    private CarResDTO car;


    public BillServiceResDTO(Long id, BigDecimal totalAmount, BigDecimal amountTax, BigDecimal totalAmountAfterTax, LocalDateTime createdAt, OrderService orderService, Car car) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.amountTax = amountTax;
        this.totalAmountAfterTax = totalAmountAfterTax;
        this.createdAt = createdAt;
        this.orderService = orderService.toOrderServiceResDTO();
        this.car = car.toCarResDTO();
    }

}
