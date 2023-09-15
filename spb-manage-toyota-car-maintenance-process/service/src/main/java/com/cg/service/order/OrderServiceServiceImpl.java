package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderService.*;
import com.cg.domain.entity.orderService.*;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.domain.enums.EStatusCarQueue;
import com.cg.domain.enums.EStatusOrderService;
import com.cg.repository.order.IOrderServiceCurrentServiceAreaRepository;
import com.cg.repository.order.IOrderServiceRepository;
import com.cg.service.service.IServiceAreaService;
import com.cg.service.service.carPlate.ICarQueueService;
import com.cg.service.service.maintenance.IMaintenanceService;
import com.cg.service.service.repair.IRepairItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderServiceServiceImpl implements IOrderServiceService {

    @Autowired
    private IOrderServiceRepository orderServiceRepository;

    @Autowired
    private IMaintenanceService maintenanceService;

    @Autowired
    private IOrderRepairItemAccessoryService orderRepairItemAccessoryService;

    @Autowired
    private IOrderMaintenanceItemAccessoryService orderMaintenanceItemAccessoryService;

    @Autowired
    private IOrderMaintenanceItemService orderMaintenanceItemService;

    @Autowired
    private IOrderMaintenanceService orderMaintenanceService;

    @Autowired
    private IOrderRepairItemService orderRepairItemService;

    @Autowired
    private IRepairItemService repairItemService;

    @Autowired
    private ICarQueueService carQueueService;

    @Autowired
    private IOrderServiceCurrentServiceAreaRepository orderServiceCurrentServiceAreaRepository;

    @Override
    public List<OrderService> findAll() {
        return orderServiceRepository.findAll();
    }

    @Override
    public Optional<OrderService> findById(Long id) {
        return orderServiceRepository.findById(id);
    }

    @Override
    public Optional<OrderService> findByIdAndDeletedIsFalse(Long id) {
        return orderServiceRepository.findByIdAndDeletedIsFalse(id);
    }

    @Override
    public OrderService save(OrderService orderService) {
        return orderServiceRepository.save(orderService);
    }

    @Override
    public void delete(OrderService orderService) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public OrderServiceResDTO getOrderServiceResDTOByIdAndStatus(Long id, EStatusOrderService status) {
        Optional<OrderServiceResDTO> optionalOrderServiceResDTO = orderServiceRepository.findOrderServiceResDTOByIdAndStatus(id, status);

        return optionalOrderServiceResDTO.get();
    }

    @Override
    public List<OrderServiceResDTO> findAllOrderServiceByStatusResDTO(EStatusOrderService status) {
        return orderServiceRepository.findAllOrderServiceByStatusResDTO(status);
    }

    @Override
    public List<OrderServiceResDTO> findAllByStatusCarQueueDoneAndDeletedIsFalse(EStatusCarQueue eStatusCarQueue) {
        return orderServiceRepository.findAllByStatusCarQueueDoneAndDeletedIsFalse(eStatusCarQueue);
    }

    @Override
    public OrderService createOrderService(OrderServiceCreateReqDTO orderServiceCreateReqDTO, CarQueue carQueue)  {
        OrderService orderService = new OrderService();
        orderService.setId(null);
        orderService.setTax(8L);
        orderService.setCarQueue(carQueue);
        orderService.setStatus(EStatusOrderService.STATUS_WAITING_PAYMENT);
        orderService.setDistance(Long.parseLong(orderServiceCreateReqDTO.getDistance()));
        orderService.setCustomerReq(orderServiceCreateReqDTO.getCustomerReq());
        orderService.setDoEarly(orderServiceCreateReqDTO.getDoEarly());
        orderServiceRepository.save(orderService);

        OrderServiceCurrentServiceArea orderServiceCurrentServiceArea = new OrderServiceCurrentServiceArea();
        orderServiceCurrentServiceArea.setOrderService(orderService);
        orderServiceCurrentServiceArea.setServiceAreaName(null);
        orderServiceCurrentServiceAreaRepository.save(orderServiceCurrentServiceArea);

        BigDecimal totalAmount = BigDecimal.ZERO;
        if (!orderServiceCreateReqDTO.getMaintenance().getMaintenanceId().equals("")) {
            Optional<Maintenance> maintenanceOptional = maintenanceService.findByIdAndDeletedIsFalse(Long.parseLong(orderServiceCreateReqDTO.getMaintenance().getMaintenanceId()));

            if(maintenanceOptional.isPresent()) {
                OrderMaintenance orderMaintenance = orderMaintenanceService.create(maintenanceOptional.get(), orderServiceCreateReqDTO.getMaintenance(), orderService);
                totalAmount = totalAmount.add(orderMaintenance.getAmount());

                List<OrderMaintenanceItem> orderMaintenanceItemList = orderMaintenanceItemService.findAllByOrderMaintenance(orderMaintenance);

                for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {

                    List<OrderMaintenanceItemAccessory> orderMaintenanceItemAccessoryList = orderMaintenanceItemAccessoryService.findAllByOrderMaintenanceItem(orderMaintenanceItem);

                    for (OrderMaintenanceItemAccessory orderMaintenanceItemAccessory : orderMaintenanceItemAccessoryList) {

                        totalAmount = totalAmount.add(orderMaintenanceItemAccessory.getAmount());
                    }
                }
            }
        }

        List<OrderRepairItem> orderRepairItemList = new ArrayList<>();

        if (orderServiceCreateReqDTO.getRepairs() != null) {
            for (OrderRepairDTO orderRepairDTO : orderServiceCreateReqDTO.getRepairs()) {
                Optional<RepairItem> repairItemOptional = repairItemService.findByIdAndDeletedIsFalse(Long.parseLong(orderRepairDTO.getRepairItemId()));
                OrderRepairItem orderRepairItem = orderRepairItemService.create(repairItemOptional.get(), orderRepairDTO, orderService);
                orderRepairItemList.add(orderRepairItem);
            }
        }

        if (!orderRepairItemList.isEmpty()) {
            for (OrderRepairItem orderRepairItem : orderRepairItemList) {
                List<OrderRepairItemAccessory> orderRepairItemAccessoryList = orderRepairItemAccessoryService.findAllByOrderRepairItem(orderRepairItem);
                totalAmount = totalAmount.add(orderRepairItem.getAmount());
                for (OrderRepairItemAccessory orderRepairItemAccessory : orderRepairItemAccessoryList) {
                    totalAmount = totalAmount.add(orderRepairItemAccessory.getAmount());
                }
            }
        }

        BigDecimal amountTax = totalAmount.multiply(BigDecimal.valueOf(orderService.getTax() * 0.01));
        BigDecimal totalAmountAfterTax = totalAmount.add(amountTax);
        orderService.setAmountTax(amountTax);
        orderService.setTotalAmount(totalAmount);
        orderService.setTotalAmountAfterTax(totalAmountAfterTax);
        orderServiceRepository.save(orderService);

        return orderService;
    }

    @Override
    public Optional<OrderService> findByCarQueueAndDeletedIsFalse(CarQueue carQueue) {
        return orderServiceRepository.findByCarQueueAndDeletedIsFalse(carQueue);
    }

    @Override
    public OrderService update(OrderService orderService, OrderServiceUpdateReqDTO orderServiceUpdateReqDTO) {

        Optional<OrderMaintenance> orderMaintenanceOptional = orderMaintenanceService.findOrderMaintenanceByOrderService(orderService);
        if (orderMaintenanceOptional.isPresent()) {
            orderMaintenanceService.update(orderServiceUpdateReqDTO.getMaintenance(), orderMaintenanceOptional.get());
        }

        List<OrderRepairItem> orderRepairItemList = orderRepairItemService.findAllByOrderService(orderService);
        for (OrderRepairDTO orderRepairDTO : orderServiceUpdateReqDTO.getRepairs()) {
            Integer count = 0;
            for (OrderRepairItem orderRepairItem : orderRepairItemList) {
                if (orderRepairItem.getRepairItem().getId() == Long.parseLong(orderRepairDTO.getRepairItemId())) {
                    orderRepairItemService.update(orderRepairItem, orderRepairDTO);
                } else {
                    count = count + 1;
                }
            }
            if (count == orderRepairItemList.size()) {
                Optional<RepairItem> repairItemOptional = repairItemService.findByIdAndDeletedIsFalse(Long.parseLong(orderRepairDTO.getRepairItemId()));
                OrderRepairItem orderRepairItemNew = orderRepairItemService.create(repairItemOptional.get(), orderRepairDTO, orderService);
                orderRepairItemList.add(orderRepairItemNew);
            }
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        Optional<OrderMaintenance> orderMaintenanceOptional1 = orderMaintenanceService.findOrderMaintenanceByOrderService(orderService);

        if (orderMaintenanceOptional1.isPresent()) {
            totalAmount = totalAmount.add(orderMaintenanceOptional1.get().getAmount());
            List<OrderMaintenanceItem> orderMaintenanceItemList = orderMaintenanceItemService.findAllByOrderMaintenance(orderMaintenanceOptional1.get());
            for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                List<OrderMaintenanceItemAccessory> orderMaintenanceItemAccessoryList = orderMaintenanceItemAccessoryService.findAllByOrderMaintenanceItem(orderMaintenanceItem);
                for (OrderMaintenanceItemAccessory orderMaintenanceItemAccessory : orderMaintenanceItemAccessoryList) {
                    totalAmount = totalAmount.add(orderMaintenanceItemAccessory.getAmount());
                }
            }
        }

        for (OrderRepairItem orderRepairItem : orderRepairItemList) {
            List<OrderRepairItemAccessory> orderRepairItemAccessoryList = orderRepairItemAccessoryService.findAllByOrderRepairItem(orderRepairItem);
            totalAmount = totalAmount.add(orderRepairItem.getAmount());
            for (OrderRepairItemAccessory orderRepairItemAccessory : orderRepairItemAccessoryList) {
                totalAmount = totalAmount.add(orderRepairItemAccessory.getAmount());
            }
        }

        orderService.setTotalAmount(totalAmount);
        orderServiceRepository.save(orderService);

        return orderService;
    }

    @Override
    public List<OrderServiceResDTO> findAllOrderServiceByServiceAreaNameAndDeletedIsFalseResDTO(String serviceAreaName) {
        return orderServiceRepository.findAllOrderServiceByServiceAreaNameAndDeletedIsFalseResDTO(serviceAreaName);
    }

    @Override
    public void updateStatusCarQueue(OrderServiceUpdateStatusCarQueueReqDTO orderServiceUpdateStatusCarQueueReqDTO) {
        Optional<OrderService> orderServiceOptional = orderServiceRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderServiceUpdateStatusCarQueueReqDTO.getOrderServiceId()));

        if (orderServiceOptional.isPresent()) {
            OrderService orderService = orderServiceOptional.get();
            Optional<CarQueue> carQueueOptional = carQueueService.findCarQueueByIdAndDeletedIsFalse(orderService.getCarQueue().getId());

            if (carQueueOptional.isPresent()) {
                CarQueue carQueue = carQueueOptional.get();
                carQueue.setStatus(EStatusCarQueue.getEStatusCarQueueByName(orderServiceUpdateStatusCarQueueReqDTO.getStatus()));
                carQueueService.save(carQueue);
            }
        }
    }


}
