package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderMaintenance.OrderMaintenanceUpdateReqDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderAccessoryDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderMaintenanceDTO;
import com.cg.domain.dto.service.orderService.orderMaintenance.OrderMaintenanceResDTO;
import com.cg.domain.entity.orderService.OrderMaintenance;
import com.cg.domain.entity.orderService.OrderMaintenanceItem;
import com.cg.domain.entity.orderService.OrderMaintenanceItemAccessory;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.entity.service.maintenance.MaintenanceItemAccessory;
import com.cg.domain.entity.service.maintenance.MaintenanceMaintenanceItem;
import com.cg.domain.enums.*;
import com.cg.repository.order.IOrderMaintenanceItemAccessoryRepository;
import com.cg.repository.order.IOrderMaintenanceItemRepository;
import com.cg.repository.order.IOrderMaintenanceRepository;
import com.cg.repository.service.maintenance.IMaintenanceItemAccessoryRepository;
import com.cg.repository.service.maintenance.IMaintenanceItemRepository;
import com.cg.repository.service.maintenance.IMaintenanceMaintenanceItemRepository;
import com.cg.repository.service.maintenance.IMaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderMaintenanceServiceImpl implements IOrderMaintenanceService {

    @Autowired
    private IOrderMaintenanceRepository orderMaintenanceRepository;

    @Autowired
    private IOrderMaintenanceItemRepository orderMaintenanceItemRepository;

    @Autowired
    private IMaintenanceItemRepository maintenanceItemRepository;

    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Autowired
    private IMaintenanceMaintenanceItemRepository maintenanceMaintenanceItemRepository;

    @Autowired
    private IMaintenanceItemAccessoryRepository maintenanceItemAccessoryRepository;

    @Autowired
    private IOrderMaintenanceItemAccessoryRepository orderMaintenanceItemAccessoryRepository;

    @Override
    public List<OrderMaintenance> findAll() {
        return orderMaintenanceRepository.findAll();
    }

    @Override
    public Optional<OrderMaintenance> findById(Long id) {
        return orderMaintenanceRepository.findById(id);
    }

    @Override
    public OrderMaintenance save(OrderMaintenance orderMaintenance) {
        return orderMaintenanceRepository.save(orderMaintenance);
    }

    @Override
    public void delete(OrderMaintenance orderMaintenance) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<OrderMaintenanceResDTO> findOrderMaintenanceResDTOByOrderServiceId(Long orderServiceId) {
        return orderMaintenanceRepository.findOrderMaintenanceResDTOByOrderServiceId(orderServiceId);
    }

    @Override
    public OrderMaintenanceResDTO updateStatus(OrderMaintenance orderMaintenance, OrderMaintenanceUpdateReqDTO orderMaintenanceUpdateReqDTO) {
        orderMaintenance.setStatus(EStatusOrderServiceDetail.getEStatusOrderServiceDetailByName(orderMaintenanceUpdateReqDTO.getStatus()));
        orderMaintenanceRepository.save(orderMaintenance);
        OrderMaintenanceResDTO orderMaintenanceResDTO = orderMaintenance.toOrderMaintenanceResDTO();
        return orderMaintenanceResDTO;
    }

    @Override
    public List<OrderMaintenance> saveAll(List<OrderMaintenance> orderMaintenanceList) {
        return orderMaintenanceRepository.saveAll(orderMaintenanceList);
    }

    @Override
    public List<OrderMaintenanceResDTO> findAllOrderMaintenanceResDTOByOrderServiceId(Long orderServiceId) {
        return orderMaintenanceRepository.findAllOrderMaintenanceResDTOByOrderServiceId(orderServiceId);
    }

    @Override
    public OrderMaintenance create(Maintenance maintenance, OrderMaintenanceDTO orderMaintenanceDTO, OrderService orderService) {
        OrderMaintenance orderMaintenance = new OrderMaintenance();
        orderMaintenance.setId(null);
        orderMaintenance.setName(maintenance.getTitle());
        orderMaintenance.setPackName(EPackService.MAINTENANCE);
        orderMaintenance.setPayment(EPayment.getEPaymentByName(orderMaintenanceDTO.getPayment()));
        orderMaintenance.setUnitWage(maintenance.getUnitWage());
        orderMaintenance.setQuantity(maintenance.getQuantityWage());
        orderMaintenance.setPriceWage(maintenance.getPriceWage());
        orderMaintenance.setAmount(maintenance.getQuantityWage().multiply(maintenance.getPriceWage()));
        orderMaintenance.setMaintenance(maintenance);
        orderMaintenance.setStatus(EStatusOrderServiceDetail.STATUS_WAITING);
        orderMaintenance.setOrderService(orderService);
        orderMaintenanceRepository.save(orderMaintenance);

        List<OrderMaintenanceItem> orderMaintenanceItemList = new ArrayList<>();
        List<MaintenanceMaintenanceItem> orderMaintenanceItemListCurrent = maintenanceMaintenanceItemRepository.findAllByMaintenanceAndNameIsNotLike(maintenance, EMaintenanceChecklist.NONE);

        for (MaintenanceMaintenanceItem item : orderMaintenanceItemListCurrent) {
            Optional<MaintenanceItem> maintenanceItemOptional = maintenanceItemRepository.findByIdAndDeletedIsFalse(item.getMaintenanceItem().getId());
            OrderMaintenanceItem orderMaintenanceItem = new OrderMaintenanceItem(
                    null,
                    maintenanceItemOptional.get().getTitle(),
                    item.getName(), EChecked.NONE, EStatusOrderServiceDetail.STATUS_WAITING,
                    maintenanceItemOptional.get(),
                    orderMaintenance);
            orderMaintenanceItemList.add(orderMaintenanceItem);
        }

        orderMaintenanceItemRepository.saveAll(orderMaintenanceItemList);

        for (String maintenanceItemId : orderMaintenanceDTO.getItems()) {
            for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                if (orderMaintenanceItem.getMaintenanceItem().getId() == Long.parseLong(maintenanceItemId)) {
                    orderMaintenanceItem.setCheckName(EChecked.DONE);
                }
            }
            orderMaintenanceItemRepository.saveAll(orderMaintenanceItemList);
        }

        List<OrderMaintenanceItemAccessory> orderMaintenanceItemAccessoryList = new ArrayList<>();

        for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
            if (orderMaintenanceItem.getCheckName().equals(EChecked.DONE)) {
                List<MaintenanceItemAccessory> maintenanceItemAccessoryList = maintenanceItemAccessoryRepository.findAllByMaintenanceItem(orderMaintenanceItem.getMaintenanceItem());

                if (!maintenanceItemAccessoryList.isEmpty()) {
                    for (MaintenanceItemAccessory maintenanceItemAccessory : maintenanceItemAccessoryList) {
                        for (OrderAccessoryDTO orderAccessoryDTO : orderMaintenanceDTO.getAccessories()) {
                            BigDecimal quantity = BigDecimal.valueOf(Double.parseDouble(orderAccessoryDTO.getQuantity()));

                            if (maintenanceItemAccessory.getAccessory().getId() == Long.parseLong(orderAccessoryDTO.getId())) {
                                OrderMaintenanceItemAccessory orderMaintenanceItemAccessory = new OrderMaintenanceItemAccessory(null,
                                        maintenanceItemAccessory.getAccessory().getName(),
                                        maintenanceItemAccessory.getAccessory().getUnit(),
                                        quantity,
                                        maintenanceItemAccessory.getAccessory().getPrice(),
                                        maintenanceItemAccessory.getAccessory().getPrice().multiply(quantity),
                                        maintenanceItemAccessory.getAccessory(), orderMaintenanceItem);
                                orderMaintenanceItemAccessoryList.add(orderMaintenanceItemAccessory);
                            }
                        }
                    }

                    orderMaintenanceItemAccessoryRepository.saveAll(orderMaintenanceItemAccessoryList);
                }
            }
        }

        return orderMaintenance;
    }

    @Override
    public OrderMaintenance update(OrderMaintenanceDTO orderMaintenanceDTO, OrderMaintenance orderMaintenance) {
        List<OrderMaintenanceItem> orderMaintenanceItemList = orderMaintenanceItemRepository.findAllByOrderMaintenance(orderMaintenance);

        for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
            orderMaintenanceItem.setCheckName(EChecked.NONE);
        }

        if (orderMaintenance.getMaintenance().getId() == Long.parseLong(orderMaintenanceDTO.getMaintenanceId())) {
            for (String maintenanceItemId : orderMaintenanceDTO.getItems()) {
                for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                    if (orderMaintenanceItem.getMaintenanceItem().getId() == Long.parseLong(maintenanceItemId)) {
                        orderMaintenanceItem.setCheckName(EChecked.DONE);
                    }
                }
            }

            orderMaintenanceItemRepository.saveAll(orderMaintenanceItemList);

            for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                List<OrderMaintenanceItemAccessory> orderMaintenanceItemAccessoryList = orderMaintenanceItemAccessoryRepository.findAllByOrderMaintenanceItem(orderMaintenanceItem);
                orderMaintenanceItemAccessoryRepository.deleteAll(orderMaintenanceItemAccessoryList);
            }

            List<OrderMaintenanceItemAccessory> orderMaintenanceItemAccessoryListNew = new ArrayList<>();

            for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                if (orderMaintenanceItem.getCheckName().equals(EChecked.DONE)) {
                    List<MaintenanceItemAccessory> maintenanceItemAccessoryList = maintenanceItemAccessoryRepository.findAllByMaintenanceItem(orderMaintenanceItem.getMaintenanceItem());
                    if (!maintenanceItemAccessoryList.isEmpty()) {
                        for (MaintenanceItemAccessory accessory : maintenanceItemAccessoryList) {
                            for (OrderAccessoryDTO orderAccessoryDTO : orderMaintenanceDTO.getAccessories()) {
                                BigDecimal quantity = BigDecimal.valueOf(Double.parseDouble(orderAccessoryDTO.getQuantity()));

                                if (accessory.getId() == Long.parseLong(orderAccessoryDTO.getId())) {
                                    OrderMaintenanceItemAccessory orderMaintenanceItemAccessory = new OrderMaintenanceItemAccessory(null,
                                            accessory.getAccessory().getName(),
                                            accessory.getAccessory().getUnit(),
                                            quantity,
                                            accessory.getAccessory().getPrice(),
                                            accessory.getAccessory().getPrice().multiply(quantity),
                                            accessory.getAccessory(), orderMaintenanceItem);
                                    orderMaintenanceItemAccessoryListNew.add(orderMaintenanceItemAccessory);
                                }
                            }
                        }

                        orderMaintenanceItemAccessoryRepository.saveAll(orderMaintenanceItemAccessoryListNew);
                    }
                }
            }
        } else {
            Optional<Maintenance> maintenanceOptional = maintenanceRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderMaintenanceDTO.getMaintenanceId()));
            orderMaintenance.setName(maintenanceOptional.get().getTitle());
            orderMaintenance.setPayment(EPayment.getEPaymentByName(orderMaintenanceDTO.getPayment()));
            orderMaintenance.setUnitWage(maintenanceOptional.get().getUnitWage());
            orderMaintenance.setQuantity(maintenanceOptional.get().getQuantityWage());
            orderMaintenance.setPriceWage(maintenanceOptional.get().getPriceWage());
            orderMaintenance.setAmount(maintenanceOptional.get().getQuantityWage().multiply(maintenanceOptional.get().getPriceWage()));
            orderMaintenance.setMaintenance(maintenanceOptional.get());
            orderMaintenanceRepository.save(orderMaintenance);

            for (String maintenanceItemId : orderMaintenanceDTO.getItems()) {
                for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                    if (orderMaintenanceItem.getMaintenanceItem().getId() == Long.parseLong(maintenanceItemId)) {
                        orderMaintenanceItem.setCheckName(EChecked.DONE);
                    }
                }
            }

            orderMaintenanceItemRepository.saveAll(orderMaintenanceItemList);

            for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                List<OrderMaintenanceItemAccessory> orderMaintenanceItemAccessoryList = orderMaintenanceItemAccessoryRepository.findAllByOrderMaintenanceItem(orderMaintenanceItem);
                orderMaintenanceItemAccessoryRepository.deleteAll(orderMaintenanceItemAccessoryList);
            }

            List<OrderMaintenanceItemAccessory> orderMaintenanceItemAccessoryListNew = new ArrayList<>();

            for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                if (orderMaintenanceItem.getCheckName().equals(EChecked.DONE)) {
                    List<MaintenanceItemAccessory> maintenanceItemAccessoryList = maintenanceItemAccessoryRepository.findAllByMaintenanceItem(orderMaintenanceItem.getMaintenanceItem());

                    if (!maintenanceItemAccessoryList.isEmpty()) {
                        for (MaintenanceItemAccessory accessory : maintenanceItemAccessoryList) {
                            for (OrderAccessoryDTO orderAccessoryDTO : orderMaintenanceDTO.getAccessories()) {
                                BigDecimal quantity = BigDecimal.valueOf(Double.parseDouble(orderAccessoryDTO.getQuantity()));

                                if (accessory.getId() == Long.parseLong(orderAccessoryDTO.getId())) {
                                    OrderMaintenanceItemAccessory orderMaintenanceItemAccessory = new OrderMaintenanceItemAccessory(null,
                                            accessory.getAccessory().getName(),
                                            accessory.getAccessory().getUnit(),
                                            quantity,
                                            accessory.getAccessory().getPrice(),
                                            accessory.getAccessory().getPrice().multiply(quantity),
                                            accessory.getAccessory(), orderMaintenanceItem);
                                    orderMaintenanceItemAccessoryListNew.add(orderMaintenanceItemAccessory);
                                }
                            }
                        }

                        orderMaintenanceItemAccessoryRepository.saveAll(orderMaintenanceItemAccessoryListNew);
                    }
                }
            }
        }

        return orderMaintenance;
    }

    @Override
    public Optional<OrderMaintenance> findOrderMaintenanceByOrderService(OrderService orderService) {
        return orderMaintenanceRepository.findOrderMaintenanceByOrderService(orderService);
    }
}
