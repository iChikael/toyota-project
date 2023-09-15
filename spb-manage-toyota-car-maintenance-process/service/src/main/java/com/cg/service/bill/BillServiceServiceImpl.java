package com.cg.service.bill;

import com.cg.domain.dto.service.billService.*;
import com.cg.domain.entity.bill.BillService;
import com.cg.domain.entity.bill.BillServiceDetail;
import com.cg.domain.entity.bill.BillServiceDetailAccessory;
import com.cg.domain.entity.orderService.*;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.enums.EStatusOrderService;
import com.cg.repository.bill.IBillServiceDetailAccessoryRepository;
import com.cg.repository.bill.IBillServiceDetailRepository;
import com.cg.repository.bill.IBillServiceRepository;
import com.cg.repository.order.*;
import com.cg.repository.service.carPlate.ICarQueueRepository;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BillServiceServiceImpl implements IBillServiceService {

    @Autowired
    private IBillServiceRepository billServiceRepository;

    @Autowired
    private IBillServiceDetailRepository billServiceDetailRepository;

    @Autowired
    private IBillServiceDetailAccessoryRepository billServiceDetailAccessoryRepository;

    @Autowired
    private IOrderServiceRepository orderServiceRepository;

    @Autowired
    private IOrderMaintenanceRepository orderMaintenanceRepository;

    @Autowired
    private IOrderMaintenanceItemRepository orderMaintenanceItemRepository;

    @Autowired
    private IOrderMaintenanceItemAccessoryRepository orderMaintenanceItemAccessoryRepository;

    @Autowired
    private IOrderRepairItemRepository orderRepairItemRepository;

    @Autowired
    private IOrderRepairItemAccessoryRepository orderRepairItemAccessoryRepository;

    @Autowired
    private ICarQueueRepository carQueueRepository;

    @Autowired
    private IOrderServiceCurrentServiceAreaRepository orderServiceCurrentServiceAreaRepository;

    @Autowired
    private AppUtils appUtils;

    @Override
    public List<BillService> findAll() {
        return billServiceRepository.findAll();
    }

    @Override
    public Optional<BillService> findById(Long id) {
        return billServiceRepository.findById(id);
    }

    @Override
    public BillService save(BillService billService) {
        return billServiceRepository.save(billService);
    }

    @Override
    public void delete(BillService billService) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public BillService create(OrderService orderService, CarQueue carQueue) {
        BillService billService = new BillService();
        billService.setId(null);
        billService.setTotalAmount(orderService.getTotalAmount());
        billService.setTax(orderService.getTax());
        billService.setAmountTax(orderService.getAmountTax());
        billService.setTotalAmountAfterTax(orderService.getTotalAmountAfterTax());
        billService.setOrderService(orderService);

        billServiceRepository.save(billService);

        List<BillServiceDetailAccessory> billServiceDetailAccessoryList = new ArrayList<>();

        List<OrderMaintenance> orderMaintenanceList = orderMaintenanceRepository.findAllByOrderServiceAndDeletedIsFalse(orderService);

        if (!orderMaintenanceList.isEmpty()) {
            for (OrderMaintenance orderMaintenance : orderMaintenanceList) {
                orderMaintenance.setDeleted(true);
//                orderMaintenance.setStatus(EStatusOrderServiceDetail.STATUS_DONE);
                orderMaintenanceRepository.save(orderMaintenance);

                BillServiceDetail billServiceDetail = orderMaintenance.toBillServiceDetail();
                billServiceDetail.setId(null);
                billServiceDetail.setBillService(billService);
                billServiceDetailRepository.save(billServiceDetail);

                List<OrderMaintenanceItem> orderMaintenanceItemList = orderMaintenanceItemRepository.findAllByOrderMaintenanceAndDeletedIsFalse(orderMaintenance);

                for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                    List<OrderMaintenanceItemAccessory> orderMaintenanceItemAccessoryList = orderMaintenanceItemAccessoryRepository.findAllByOrderMaintenanceItemAndDeletedIsFalse(orderMaintenanceItem);

                    for (OrderMaintenanceItemAccessory orderMaintenanceItemAccessory : orderMaintenanceItemAccessoryList) {
                        BillServiceDetailAccessory billServiceDetailAccessory = orderMaintenanceItemAccessory.toBillServiceDetailAccessory();
                        billServiceDetailAccessory.setId(null);
                        billServiceDetailAccessory.setBillServiceDetail(billServiceDetail);
                        billServiceDetailAccessoryList.add(billServiceDetailAccessory);

                        orderMaintenanceItemAccessory.setDeleted(true);
                        orderMaintenanceItemAccessoryRepository.save(orderMaintenanceItemAccessory);
                    }

                    orderMaintenanceItem.setDeleted(true);
                    orderMaintenanceItemRepository.save(orderMaintenanceItem);
                }
            }
        }

        List<OrderRepairItem> orderRepairItemList = orderRepairItemRepository.findAllByOrderServiceAndDeletedIsFalse(orderService);

        if (!orderRepairItemList.isEmpty()) {
            for (OrderRepairItem orderRepairItem : orderRepairItemList) {
                BillServiceDetail billServiceDetail = orderRepairItem.toBillServiceDetail();
                billServiceDetail.setId(null);
                billServiceDetail.setBillService(billService);

                billServiceDetailRepository.save(billServiceDetail);

                List<OrderRepairItemAccessory> orderRepairItemAccessoryList = orderRepairItemAccessoryRepository.findAllByOrderRepairItemAndDeletedIsFalse(orderRepairItem);

                for (OrderRepairItemAccessory orderRepairItemAccessory : orderRepairItemAccessoryList) {
                    BillServiceDetailAccessory billServiceDetailAccessory = orderRepairItemAccessory.toBillServiceDetailAccessory();
                    billServiceDetailAccessory.setId(null);
                    billServiceDetailAccessory.setBillServiceDetail(billServiceDetail);
                    billServiceDetailAccessoryList.add(billServiceDetailAccessory);

                    orderRepairItemAccessory.setDeleted(true);
                    orderRepairItemAccessoryRepository.save(orderRepairItemAccessory);
                }

                orderRepairItem.setDeleted(true);
//                orderRepairItem.setStatus(EStatusOrderServiceDetail.STATUS_DONE);
                orderRepairItemRepository.save(orderRepairItem);
            }
        }

        billServiceDetailAccessoryRepository.saveAll(billServiceDetailAccessoryList);

        Optional<OrderServiceCurrentServiceArea> orderServiceCurrentServiceAreaOptional = orderServiceCurrentServiceAreaRepository.findByOrderServiceIdAndDeletedIsFalse(orderService.getId());
        OrderServiceCurrentServiceArea orderServiceCurrentServiceArea = orderServiceCurrentServiceAreaOptional.get();
        orderServiceCurrentServiceArea.setDeleted(true);
        orderServiceCurrentServiceAreaRepository.save(orderServiceCurrentServiceArea);

        orderService.setDeleted(true);
        orderService.setStatus(EStatusOrderService.STATUS_DONE);
        orderServiceRepository.save(orderService);

        carQueue.setDeleted(true);
//        carQueue.setStatus(EStatusCarQueue.STATUS_DONE);
        carQueueRepository.save(carQueue);

        return billService;
    }

    @Override
    public List<BillServiceResDTO> fillAllBillServiceResDTO() {
        return billServiceRepository.fillAllBillServiceResDTO();
    }

    @Override
    public Optional<BillServiceResDTO> findBillServiceResDTOById(Long id) {
        return billServiceRepository.findBillServiceResDTOById(id);
    }

    @Override
    public List<BillServiceResDTO> findAllBillServiceResDTOByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return billServiceRepository.findAllBillServiceResDTOByCreatedAtBetween(startDateTime, endDateTime);
    }

    @Override
    public List<IBillServiceResDTO> fillAllBillServiceTopFiveResDTO() {
        return billServiceRepository.fillAllBillServiceTopFiveResDTO();
    }

    @Override
    public RevenueByYearDTO findRevenueByYear(Integer year) {
        LocalDateTime startDateTime = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(year, 12, 31, 23, 59, 59);

        List<BillServiceResDTO> billServiceResDTOListByYear = billServiceRepository.findAllBillServiceResDTOByCreatedAtBetween(startDateTime, endDateTime);
        List<RevenueByMonthDTO> revenueByMonthDTOS = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            LocalDateTime startDate = LocalDateTime.of(year, i, 1, 0, 0);
            LocalDateTime endDate = startDate.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

            List<BillServiceResDTO> billServiceResDTOListByMonth = billServiceRepository.findAllBillServiceResDTOByCreatedAtBetween(startDate, endDate);

            RevenueByMonthDTO revenueByMonthDTO = new RevenueByMonthDTO();
            revenueByMonthDTO.setMonth(Long.parseLong(String.valueOf(i)));

            if (billServiceResDTOListByMonth.isEmpty()) {
                revenueByMonthDTO.setTotalAmountByMonth(BigDecimal.ZERO);
            }
            else {
                BigDecimal totalAmountByMonth = BigDecimal.ZERO;

                for (BillServiceResDTO billServiceResDTO : billServiceResDTOListByMonth) {
                    totalAmountByMonth = totalAmountByMonth.add(billServiceResDTO.getTotalAmountAfterTax());
                }

                revenueByMonthDTO.setTotalAmountByMonth(totalAmountByMonth);
            }

            revenueByMonthDTOS.add(revenueByMonthDTO);
        }

        RevenueByYearDTO revenueByYearDTO = new RevenueByYearDTO();

        if (billServiceResDTOListByYear.isEmpty()) {
            revenueByYearDTO.setTotalAmountByYear(BigDecimal.ZERO);
        }
        else {
            BigDecimal totalAmountByYear = BigDecimal.ZERO;

            for (BillServiceResDTO billServiceResDTO : billServiceResDTOListByYear) {
                totalAmountByYear = totalAmountByYear.add(billServiceResDTO.getTotalAmountAfterTax());
            }

            revenueByYearDTO.setTotalAmountByYear(totalAmountByYear);
        }

        revenueByYearDTO.setRevenueByMonthDTOS(revenueByMonthDTOS);
        revenueByYearDTO.setBillServiceResDTOList(billServiceResDTOListByYear);

        return revenueByYearDTO;
    }

    @Override
    public RevenueByMonthDTO findRevenueByMonth(String month) {
        LocalDateTime startDateTime = LocalDateTime.of(Integer.parseInt(month.substring(0, 4)), Integer.parseInt(month.substring(5, 7)), 1, 0, 0);
        LocalDateTime endDateTime = startDateTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

        List<BillServiceResDTO> billServiceResDTOListByMonth = billServiceRepository.findAllBillServiceResDTOByCreatedAtBetween(startDateTime, endDateTime);

        RevenueByMonthDTO revenueByMonthDTO = new RevenueByMonthDTO();
        revenueByMonthDTO.setMonth(Long.parseLong(month.substring(5, 7)));

        List<RevenueByDayDTO> revenueByDayDTOS = new ArrayList<>();

        for (int i = 1; i <= Integer.parseInt(appUtils.convertLocalDateTimeToString(endDateTime).substring(0, 2)); i++) {
            LocalDateTime startDate;
            LocalDateTime endDate;

            if (i < 10) {
                startDate = LocalDateTime.parse(month + "-0" + i + "T00:00:00");
                endDate = LocalDateTime.parse(month + "-0" + i + "T23:59:59.99");
            }
            else {
                startDate = LocalDateTime.parse(month + "-" + i + "T00:00:00");
                endDate = LocalDateTime.parse(month + "-" + i + "T23:59:59.99");
            }

            List<BillServiceResDTO> billServiceResDTOListByDay = billServiceRepository.findAllBillServiceResDTOByCreatedAtBetween(startDate, endDate);

            RevenueByDayDTO revenueByDayDTO = new RevenueByDayDTO();
            revenueByDayDTO.setDay((long) i);

            if (billServiceResDTOListByDay.isEmpty()) {
                revenueByDayDTO.setTotalAmountByDay(BigDecimal.ZERO);
            }
            else {
                BigDecimal totalAmountByDay = BigDecimal.ZERO;

                for (BillServiceResDTO billServiceResDTO : billServiceResDTOListByDay) {
                    totalAmountByDay = totalAmountByDay.add(billServiceResDTO.getTotalAmountAfterTax());
                }

                revenueByDayDTO.setTotalAmountByDay(totalAmountByDay);
            }

            revenueByDayDTOS.add(revenueByDayDTO);
        }

        if (billServiceResDTOListByMonth.isEmpty()) {
            revenueByMonthDTO.setTotalAmountByMonth(BigDecimal.ZERO);
        }
        else {
            BigDecimal totalAmountByMonth = BigDecimal.ZERO;

            for (BillServiceResDTO billServiceResDTO : billServiceResDTOListByMonth) {
                totalAmountByMonth = totalAmountByMonth.add(billServiceResDTO.getTotalAmountAfterTax());
            }

            revenueByMonthDTO.setTotalAmountByMonth(totalAmountByMonth);
        }

        revenueByMonthDTO.setBillServiceResDTOList(billServiceResDTOListByMonth);
        revenueByMonthDTO.setRevenueByDayDTOS(revenueByDayDTOS);

        return revenueByMonthDTO;
    }

    @Override
    public RevenueByDayDTO findRevenueByDay(String date) {
        LocalDateTime startDateTime = LocalDateTime.parse(date + "T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse(date + "T23:59:59.99");

        List<BillServiceResDTO> billServiceResDTOListByDay = billServiceRepository.findAllBillServiceResDTOByCreatedAtBetween(startDateTime, endDateTime);

        RevenueByDayDTO revenueByDayDTO = new RevenueByDayDTO();
        revenueByDayDTO.setDay(Long.parseLong(date.substring(8, 10)));

        if (billServiceResDTOListByDay.isEmpty()) {
            revenueByDayDTO.setTotalAmountByDay(BigDecimal.ZERO);
        }
        else {
            BigDecimal totalAmountByMonth = BigDecimal.ZERO;

            for (BillServiceResDTO billServiceResDTO : billServiceResDTOListByDay) {
                totalAmountByMonth = totalAmountByMonth.add(billServiceResDTO.getTotalAmountAfterTax());
            }

            revenueByDayDTO.setTotalAmountByDay(totalAmountByMonth);
        }

        revenueByDayDTO.setBillServiceResDTOList(billServiceResDTOListByDay);
        return revenueByDayDTO;
    }

    @Override
    public RevenueByPeriodDTO findRevenueByPeriod(String dateStart, String dateEnd) {
        LocalDateTime startDateTime = LocalDateTime.parse(dateStart + "T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse(dateEnd + "T23:59:59.99");

        List<BillServiceResDTO> billServiceResDTOListByPeriod = billServiceRepository.findAllBillServiceResDTOByCreatedAtBetween(startDateTime, endDateTime);

        RevenueByPeriodDTO revenueByPeriodDTO = new RevenueByPeriodDTO();

        if (billServiceResDTOListByPeriod.isEmpty()) {
            revenueByPeriodDTO.setTotalAmountByPeriod(BigDecimal.ZERO);
        }
        else {
            BigDecimal totalAmountByMonth = BigDecimal.ZERO;

            for (BillServiceResDTO billServiceResDTO : billServiceResDTOListByPeriod) {
                totalAmountByMonth = totalAmountByMonth.add(billServiceResDTO.getTotalAmountAfterTax());
            }

            revenueByPeriodDTO.setTotalAmountByPeriod(totalAmountByMonth);
        }

        revenueByPeriodDTO.setBillServiceResDTOList(billServiceResDTOListByPeriod);

        return revenueByPeriodDTO;
    }

}
