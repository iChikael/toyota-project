package com.cg.domain.entity.bill;

import com.cg.domain.dto.service.billService.BillServiceResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.orderService.OrderService;
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
@Table(name = "bill_service")
public class BillService extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal totalAmount;

    private Long tax;

    @Column(name = "amount_tax", precision = 10, scale = 0)
    private BigDecimal amountTax;

    @Column(name = "total_amount_after_tax", precision = 10, scale = 0)
    private BigDecimal totalAmountAfterTax;

    @OneToOne
    private OrderService orderService;

    public BillServiceResDTO toBillServiceResDTO() {
        return new BillServiceResDTO()
                .setId(id)
                .setTotalAmount(totalAmount)
                .setTotalAmountAfterTax(totalAmountAfterTax)
                .setOrderService(orderService.toOrderServiceResDTO());
    }
}
