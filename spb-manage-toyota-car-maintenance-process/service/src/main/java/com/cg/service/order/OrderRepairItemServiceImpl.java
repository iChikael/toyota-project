package com.cg.service.order;


import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemReqDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItemAccessory.OrderMaintenanceItemAccessoryResDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemReqDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemUpdateReqDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemUpdateStatusReqDTO;
import com.cg.domain.dto.service.orderService.orderRepairItemAccessory.OrderRepairItemAccessoryResDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderAccessoryDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderRepairDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemResDTO;
import com.cg.domain.entity.orderService.*;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.domain.entity.service.repair.RepairItemAccessory;
import com.cg.domain.enums.EPackService;
import com.cg.domain.enums.EPayment;
import com.cg.domain.enums.EStatusOrderServiceDetail;
import com.cg.repository.order.IOrderRepairItemAccessoryRepository;
import com.cg.repository.order.IOrderRepairItemRepository;
import com.cg.repository.order.IOrderServiceRepository;
import com.cg.repository.service.repair.IRepairItemAccessoryRepository;
import com.cg.repository.service.serviceArea.IServiceAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderRepairItemServiceImpl implements IOrderRepairItemService {

    @Autowired
    private IOrderServiceRepository orderServiceRepository;

    @Autowired
    private IServiceAreaRepository serviceAreaRepository;

    @Autowired
    private IOrderRepairItemRepository orderRepairItemRepository;

    @Autowired
    private IOrderRepairItemAccessoryRepository orderRepairItemAccessoryRepository;

    @Autowired
    private IRepairItemAccessoryRepository repairItemAccessoryRepository;

    @Override
    public List<OrderRepairItem> findAll() {
        return orderRepairItemRepository.findAll();
    }

    @Override
    public Optional<OrderRepairItem> findById(Long id) {
        return orderRepairItemRepository.findById(id);
    }

    @Override
    public OrderRepairItem save(OrderRepairItem orderRepairItem) {
        return orderRepairItemRepository.save(orderRepairItem);
    }

    @Override
    public void delete(OrderRepairItem orderRepairItem) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<OrderRepairItem> saveAll(List<OrderRepairItem> orderRepairItemList) {
        return orderRepairItemRepository.saveAll(orderRepairItemList);
    }

    @Override
    public List<OrderRepairItemResDTO> findAllOrderRepairItemResDTOByOrderServiceId(Long orderServiceId) {
        List<OrderRepairItemResDTO> orderRepairItemResDTOList = orderRepairItemRepository.findAllOrderRepairItemResDTOByOrderServiceId(orderServiceId);

        for (OrderRepairItemResDTO orderRepairItemResDTO : orderRepairItemResDTOList) {
            List<OrderRepairItemAccessoryResDTO> orderRepairItemAccessoryResDTOList = orderRepairItemAccessoryRepository.findAllOrderRepairItemAccessoryResDTOByOrderRepairItemId(orderRepairItemResDTO.getId());
            orderRepairItemResDTO.setOrderRepairItemAccessories(orderRepairItemAccessoryResDTOList);
        }

        return orderRepairItemResDTOList;
    }

    @Override
    public OrderRepairItem create(RepairItem repairItem, OrderRepairDTO orderRepairDTO, OrderService orderService) {
        OrderRepairItem orderRepairItem = new OrderRepairItem();
        orderRepairItem.setId(null);
        orderRepairItem.setName(repairItem.getTitle());
        orderRepairItem.setPackName(EPackService.REPAIR);
        orderRepairItem.setPayment(EPayment.getEPaymentByName(orderRepairDTO.getPayment()));
        orderRepairItem.setUnitWage(orderRepairDTO.getUnitWage());
        orderRepairItem.setRepairItem(repairItem);
        BigDecimal quantityRepairItem = BigDecimal.valueOf(Double.parseDouble(orderRepairDTO.getQuantity()));
        orderRepairItem.setQuantity(quantityRepairItem);
        orderRepairItem.setPriceWage(BigDecimal.valueOf(Long.parseLong(orderRepairDTO.getPriceWage())));
        orderRepairItem.setAmount(quantityRepairItem.multiply(BigDecimal.valueOf(Long.parseLong(orderRepairDTO.getPriceWage()))));
        orderRepairItem.setStatus(EStatusOrderServiceDetail.STATUS_WAITING);
        orderRepairItem.setOrderService(orderService);
        orderRepairItemRepository.save(orderRepairItem);

        List<OrderRepairItemAccessory> orderRepairItemAccessoryList = new ArrayList<>();
        List<RepairItemAccessory> repairItemAccessoryList = repairItemAccessoryRepository.findAllByRepairItem(repairItem);

        for (RepairItemAccessory repairItemAccessory : repairItemAccessoryList) {
            for (OrderAccessoryDTO orderAccessoryDTO : orderRepairDTO.getAccessories()) {
                BigDecimal quantity = BigDecimal.valueOf(Double.parseDouble(orderAccessoryDTO.getQuantity()));

                if (repairItemAccessory.getAccessory().getId() == Long.parseLong(orderAccessoryDTO.getId())) {
                    OrderRepairItemAccessory orderRepairItemAccessory = new OrderRepairItemAccessory(
                            null,
                            repairItemAccessory.getAccessory().getName(),
                            repairItemAccessory.getAccessory().getUnit(),
                            quantity,
                            repairItemAccessory.getAccessory().getPrice(),
                            repairItemAccessory.getAccessory().getPrice().multiply(quantity),
                            repairItemAccessory.getAccessory(), orderRepairItem);
                    orderRepairItemAccessoryList.add(orderRepairItemAccessory);
                }
            }
        }

        orderRepairItemAccessoryRepository.saveAll(orderRepairItemAccessoryList);

        return orderRepairItem;
    }

    @Override
    public List<OrderRepairItem> findAllByOrderService(OrderService orderService) {
        return orderRepairItemRepository.findAllByOrderService(orderService);
    }

    @Override
    public OrderRepairItem update(OrderRepairItem orderRepairItem, OrderRepairDTO orderRepairDTO) {
        orderRepairItem.setPayment(EPayment.getEPaymentByName(orderRepairDTO.getPayment()));
        orderRepairItem.setUnitWage(orderRepairDTO.getUnitWage());
        BigDecimal quantityRepairItem = BigDecimal.valueOf(Double.parseDouble(orderRepairDTO.getQuantity()));
        orderRepairItem.setQuantity(quantityRepairItem);
        orderRepairItem.setPriceWage(BigDecimal.valueOf(Long.parseLong(orderRepairDTO.getPriceWage())));
        orderRepairItem.setAmount(quantityRepairItem.multiply(BigDecimal.valueOf(Long.parseLong(orderRepairDTO.getPriceWage()))));

        List<OrderRepairItemAccessory> orderRepairItemAccessoryList = orderRepairItemAccessoryRepository.findAllByOrderRepairItem(orderRepairItem);

        for (OrderRepairItemAccessory orderRepairItemAccessory : orderRepairItemAccessoryList) {
            for (OrderAccessoryDTO orderAccessoryDTO : orderRepairDTO.getAccessories()) {
                RepairItemAccessory repairItemAccessory = repairItemAccessoryRepository.findByIdAndRepairItem(Long.parseLong(orderAccessoryDTO.getId()), orderRepairItem.getRepairItem());

                if (orderRepairItemAccessory.getAccessory().getId() == repairItemAccessory.getAccessory().getId()) {
                    BigDecimal quantity = BigDecimal.valueOf(Double.parseDouble(orderAccessoryDTO.getQuantity()));
                    orderRepairItemAccessory.setAccessoryQuantity(quantity);
                    orderRepairItemAccessory.setAmount(orderRepairItemAccessory.getAccessoryPrice().multiply(quantity));
                }
            }
        }

        orderRepairItemAccessoryRepository.saveAll(orderRepairItemAccessoryList);
        orderRepairItemRepository.save(orderRepairItem);

        return orderRepairItem;
    }

    @Override
    public List<OrderRepairItemResDTO> findAllOrderRepairItemByServiceAreaIdResDTO(OrderRepairItemReqDTO orderRepairItemReqDTO) {
        List<OrderRepairItemResDTO> orderRepairItemResDTOList = orderRepairItemRepository.findAllRepairItemByOrderServiceIdAndServiceAreaId(Long.parseLong(orderRepairItemReqDTO.getOrderServiceId()), Long.parseLong(orderRepairItemReqDTO.getServiceAreaId()));

        for (OrderRepairItemResDTO orderRepairItemResDTO : orderRepairItemResDTOList) {
            List<OrderRepairItemAccessoryResDTO> orderRepairItemAccessoryResDTOList = orderRepairItemAccessoryRepository.findAllOrderRepairItemAccessoryResDTOByOrderRepairItemId(orderRepairItemResDTO.getId());
            orderRepairItemResDTO.setOrderRepairItemAccessories(orderRepairItemAccessoryResDTOList);
        }

        return orderRepairItemResDTOList;
    }

    @Override
    public List<OrderRepairItemResDTO> updateStatus(OrderRepairItemUpdateReqDTO orderRepairItemUpdateReqDTO) {
        Optional<OrderService> orderServiceOptional = orderServiceRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderRepairItemUpdateReqDTO.getOrderServiceId()));

        if (orderServiceOptional.isPresent()) {
            Optional<ServiceArea> serviceAreaOptional = serviceAreaRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderRepairItemUpdateReqDTO.getServiceAreaId()));
            if (serviceAreaOptional.isPresent()) {
                List<OrderRepairItem> orderRepairItemList = orderRepairItemRepository.findAllByOrderServiceAndRepairItemServiceArea(orderServiceOptional.get(), serviceAreaOptional.get());
                if (!orderRepairItemList.isEmpty()) {
                    for (OrderRepairItem orderRepairItem : orderRepairItemList) {
                        orderRepairItem.setStatus(EStatusOrderServiceDetail.getEStatusOrderServiceDetailByName(orderRepairItemUpdateReqDTO.getStatus()));
                    }

                    orderRepairItemRepository.saveAll(orderRepairItemList);
                    List<OrderRepairItemResDTO> orderRepairItemResDTOList = new ArrayList<>();

                    for (OrderRepairItem orderRepairItem : orderRepairItemList) {
                        orderRepairItemResDTOList.add(orderRepairItem.toOrderRepairItemResDTO());
                    }

                    return orderRepairItemResDTOList;
                }
            }
        }

        return null;
    }

    @Override
    public List<OrderRepairItemResDTO> updateStatusDone(OrderRepairItemUpdateStatusReqDTO orderRepairItemUpdateStatusReqDTO) {
        Optional<OrderService> orderServiceOptional = orderServiceRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderRepairItemUpdateStatusReqDTO.getOrderServiceId()));

        if (orderServiceOptional.isPresent()) {
            Optional<ServiceArea> serviceAreaOptional = serviceAreaRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderRepairItemUpdateStatusReqDTO.getServiceAreaId()));
            if (serviceAreaOptional.isPresent()) {
                List<OrderRepairItem> orderRepairItemList = orderRepairItemRepository.findAllByOrderServiceAndRepairItemServiceArea(orderServiceOptional.get(), serviceAreaOptional.get());
                if (!orderRepairItemList.isEmpty()) {
                    for (OrderRepairItem orderRepairItem : orderRepairItemList) {
                        if (orderRepairItem.getId() == Long.parseLong(orderRepairItemUpdateStatusReqDTO.getRepairItemId())) {
                            orderRepairItem.setStatus(EStatusOrderServiceDetail.getEStatusOrderServiceDetailByName(orderRepairItemUpdateStatusReqDTO.getStatus()));
                        }
                    }

                    orderRepairItemRepository.saveAll(orderRepairItemList);
                    List<OrderRepairItemResDTO> orderRepairItemResDTOList = new ArrayList<>();

                    for (OrderRepairItem orderRepairItem : orderRepairItemList) {
                        orderRepairItemResDTOList.add(orderRepairItem.toOrderRepairItemResDTO());
                    }

                    for (OrderRepairItemResDTO orderRepairItemResDTO : orderRepairItemResDTOList) {
                        List<OrderRepairItemAccessoryResDTO> orderRepairItemAccessoryResDTOList = orderRepairItemAccessoryRepository.findAllOrderRepairItemAccessoryResDTOByOrderRepairItemId(orderRepairItemResDTO.getId());
                        orderRepairItemResDTO.setOrderRepairItemAccessories(orderRepairItemAccessoryResDTOList);
                    }

                    return orderRepairItemResDTOList;
                }
            }
        }

        return null;
    }

    @Override
    public List<OrderRepairItemResDTO> updateStatusDoneAll(OrderRepairItemUpdateStatusReqDTO orderRepairItemUpdateStatusReqDTO) {
        Optional<OrderService> orderServiceOptional = orderServiceRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderRepairItemUpdateStatusReqDTO.getOrderServiceId()));

        if (orderServiceOptional.isPresent()) {
            Optional<ServiceArea> serviceAreaOptional = serviceAreaRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderRepairItemUpdateStatusReqDTO.getServiceAreaId()));
            if (serviceAreaOptional.isPresent()) {
                List<OrderRepairItem> orderRepairItemList = orderRepairItemRepository.findAllByOrderServiceAndRepairItemServiceArea(orderServiceOptional.get(), serviceAreaOptional.get());
                if (!orderRepairItemList.isEmpty()) {
                    for (OrderRepairItem orderRepairItem : orderRepairItemList) {
                        orderRepairItem.setStatus(EStatusOrderServiceDetail.getEStatusOrderServiceDetailByName(orderRepairItemUpdateStatusReqDTO.getStatus()));
                    }

                    orderRepairItemRepository.saveAll(orderRepairItemList);
                    List<OrderRepairItemResDTO> orderRepairItemResDTOList = new ArrayList<>();

                    for (OrderRepairItem orderRepairItem : orderRepairItemList) {
                        orderRepairItemResDTOList.add(orderRepairItem.toOrderRepairItemResDTO());
                    }

                    for (OrderRepairItemResDTO orderRepairItemResDTO : orderRepairItemResDTOList) {
                        List<OrderRepairItemAccessoryResDTO> orderRepairItemAccessoryResDTOList = orderRepairItemAccessoryRepository.findAllOrderRepairItemAccessoryResDTOByOrderRepairItemId(orderRepairItemResDTO.getId());
                        orderRepairItemResDTO.setOrderRepairItemAccessories(orderRepairItemAccessoryResDTOList);
                    }

                    return orderRepairItemResDTOList;
                }
            }
        }

        return null;
    }

}
