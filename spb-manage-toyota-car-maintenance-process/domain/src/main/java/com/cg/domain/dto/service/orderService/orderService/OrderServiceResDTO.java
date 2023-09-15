package com.cg.domain.dto.service.orderService.orderService;

import com.cg.domain.dto.car.CarResDTO;
import com.cg.domain.dto.service.carqueue.CarQueueResDTO;
import com.cg.domain.entity.car.Car;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.enums.EStatusOrderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderServiceResDTO {
    private Long id;
    private BigDecimal totalAmount;
    private Long distance;
    private String customerReq;
    private String doEarly;
    private Long tax;
    private BigDecimal amountTax;
    private BigDecimal totalAmountAfterTax;
    private String statusPayment;
    private String currentServiceArea;
    private CarQueueResDTO carQueue;
    private CarResDTO car;

    public OrderServiceResDTO (Long id,BigDecimal totalAmount, Long distance, String customerReq, String doEarly, Long tax, BigDecimal amountTax, BigDecimal totalAmountAfterTax, EStatusOrderService status, String currentServiceArea, CarQueue carQueue, Car car) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.distance = distance;
        this.customerReq = customerReq;
        this.doEarly = doEarly;
        this.tax = tax;
        this.amountTax = amountTax;
        this.totalAmountAfterTax = totalAmountAfterTax;
        this.statusPayment = status.getValue();
        this.currentServiceArea = currentServiceArea;
        this.carQueue = carQueue.toCarQueueResDTO();
        this.car = car.toCarResDTO();
    }
}
