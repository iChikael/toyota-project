package com.cg.domain.entity.orderService;

import com.cg.domain.dto.service.orderService.orderService.OrderServiceResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.enums.EStatusOrderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_service")
public class OrderService extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount", precision = 10, scale = 0)
    private BigDecimal totalAmount;

    private Long distance;

    @Lob
    @Column(name = "customer_req")
    private String customerReq;

    @Lob
    @Column(name = "do_early")
    private String doEarly;

    private Long tax;

    @Column(name = "amount_tax", precision = 10, scale = 0)
    private BigDecimal amountTax;

    @Column(name = "total_amount_after_tax", precision = 10, scale = 0)
    private BigDecimal totalAmountAfterTax;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name", length = 50)
    private EStatusOrderService status;

    @OneToOne
    private CarQueue carQueue;

    public OrderServiceResDTO toOrderServiceResDTO() {
        return new OrderServiceResDTO()
                .setId(id)
                .setDistance(distance)
                .setCustomerReq(customerReq)
                .setDoEarly(doEarly)
                .setCarQueue(carQueue.toCarQueueResDTO());

    }
}
