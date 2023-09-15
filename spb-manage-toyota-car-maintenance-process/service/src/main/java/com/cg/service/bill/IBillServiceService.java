package com.cg.service.bill;

import com.cg.domain.dto.service.billService.*;
import com.cg.domain.entity.bill.BillService;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.CarQueue;
import com.cg.service.IGeneralService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IBillServiceService extends IGeneralService<BillService, Long> {
    BillService create(OrderService orderService, CarQueue carQueue);

    List<BillServiceResDTO> fillAllBillServiceResDTO();

    Optional<BillServiceResDTO> findBillServiceResDTOById(Long id);

    List<BillServiceResDTO> findAllBillServiceResDTOByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<IBillServiceResDTO> fillAllBillServiceTopFiveResDTO();

    RevenueByYearDTO findRevenueByYear(Integer year);

    RevenueByMonthDTO findRevenueByMonth(String month);

    RevenueByDayDTO findRevenueByDay(String date);

    RevenueByPeriodDTO findRevenueByPeriod(String dateStart, String dateEnd);
}
