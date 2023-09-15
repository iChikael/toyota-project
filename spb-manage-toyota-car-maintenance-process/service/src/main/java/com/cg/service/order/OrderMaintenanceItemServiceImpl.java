package com.cg.service.order;


import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemReqDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemUpdateReqDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemUpdateStatusReqDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItemAccessory.OrderMaintenanceItemAccessoryResDTO;
import com.cg.domain.entity.orderService.OrderMaintenance;
import com.cg.domain.entity.orderService.OrderMaintenanceItem;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.enums.EStatusOrderService;
import com.cg.domain.enums.EStatusOrderServiceDetail;
import com.cg.repository.order.IOrderMaintenanceItemAccessoryRepository;
import com.cg.repository.order.IOrderMaintenanceItemRepository;
import com.cg.repository.order.IOrderMaintenanceRepository;
import com.cg.repository.order.IOrderServiceRepository;
import com.cg.repository.service.serviceArea.IServiceAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderMaintenanceItemServiceImpl implements IOrderMaintenanceItemService {

    @Autowired
    private IOrderServiceRepository orderServiceRepository;

    @Autowired
    private IOrderMaintenanceItemRepository orderMaintenanceItemRepository;

    @Autowired
    private IOrderMaintenanceItemAccessoryRepository orderMaintenanceItemAccessoryRepository;

    @Autowired
    private IOrderMaintenanceRepository orderMaintenanceRepository;

    @Autowired
    private IServiceAreaRepository serviceAreaRepository;

    @Override
    public List<OrderMaintenanceItem> findAll() {
        return orderMaintenanceItemRepository.findAll();
    }

    @Override
    public Optional<OrderMaintenanceItem> findById(Long id) {
        return orderMaintenanceItemRepository.findById(id);
    }

    @Override
    public OrderMaintenanceItem save(OrderMaintenanceItem orderMaintenanceItem) {
        return orderMaintenanceItemRepository.save(orderMaintenanceItem);
    }

    @Override
    public List<OrderMaintenanceItem> saveAll(List<OrderMaintenanceItem> orderMaintenanceItemList) {
        return orderMaintenanceItemRepository.saveAll(orderMaintenanceItemList);
    }

    @Override
    public void delete(OrderMaintenanceItem orderMaintenanceItem) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<OrderMaintenanceItemResDTO> findAllOrderMaintenanceItemResDTOByOrderMaintenanceId(Long orderMaintenanceId) {
        List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = orderMaintenanceItemRepository.findAllOrderMaintenanceItemResDTOByOrderMaintenanceId(orderMaintenanceId);

        for (OrderMaintenanceItemResDTO orderMaintenanceItemResDTO : orderMaintenanceItemResDTOList) {
            List<OrderMaintenanceItemAccessoryResDTO> orderMaintenanceItemAccessoryResDTOList = orderMaintenanceItemAccessoryRepository.findAllOrderMaintenanceItemAccessoryResDTOByOrderMaintenanceItemId(orderMaintenanceItemResDTO.getId());
            orderMaintenanceItemResDTO.setOrderMaintenanceItemAccessories(orderMaintenanceItemAccessoryResDTOList);
        }

        return orderMaintenanceItemResDTOList;
    }

    @Override
    public List<OrderMaintenanceItem> findAllByOrderMaintenance(OrderMaintenance orderMaintenance) {
        return orderMaintenanceItemRepository.findAllByOrderMaintenance(orderMaintenance);
    }

    @Override
    public List<OrderMaintenanceItemResDTO> findAllOrderMaintenanceItemByServiceAreaIdResDTO(OrderMaintenanceItemReqDTO orderMaintenanceItemReqDTO) {
        Optional<OrderService> orderServiceOptional = orderServiceRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderMaintenanceItemReqDTO.getOrderServiceId()));

        if (orderServiceOptional.isPresent()) {
            Optional<OrderMaintenance> orderMaintenanceOptional = orderMaintenanceRepository.findOrderMaintenanceByOrderService(orderServiceOptional.get());

            if (orderMaintenanceOptional.isPresent()) {
                List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = orderMaintenanceItemRepository.findAllOrderMaintenanceItemResDTOByOrderMaintenanceIdAndServiceAreaId(orderMaintenanceOptional.get().getId(), Long.parseLong(orderMaintenanceItemReqDTO.getServiceAreaId()));

                for (OrderMaintenanceItemResDTO orderMaintenanceItemResDTO : orderMaintenanceItemResDTOList) {
                    List<OrderMaintenanceItemAccessoryResDTO> orderMaintenanceItemAccessoryResDTOList = orderMaintenanceItemAccessoryRepository.findAllOrderMaintenanceItemAccessoryResDTOByOrderMaintenanceItemId(orderMaintenanceItemResDTO.getId());
                    orderMaintenanceItemResDTO.setOrderMaintenanceItemAccessories(orderMaintenanceItemAccessoryResDTOList);
                }

                return orderMaintenanceItemResDTOList;
            }
        }

        return null;
    }

    @Override
    public List<OrderMaintenanceItemResDTO> updateStatus(OrderMaintenanceItemUpdateReqDTO orderMaintenanceItemUpdateReqDTO) {
        Optional<OrderMaintenance> orderMaintenanceOptional = orderMaintenanceRepository.findById(Long.parseLong(orderMaintenanceItemUpdateReqDTO.getOrderMaintenanceId()));

        if (orderMaintenanceOptional.isPresent()) {
            Optional<ServiceArea> serviceAreaOptional = serviceAreaRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderMaintenanceItemUpdateReqDTO.getServiceAreaId()));

            if (serviceAreaOptional.isPresent()) {
                List<OrderMaintenanceItem> orderMaintenanceItemList = orderMaintenanceItemRepository.findAllByOrderMaintenanceAndMaintenanceItemServiceArea(orderMaintenanceOptional.get(), serviceAreaOptional.get());

                if (!orderMaintenanceItemList.isEmpty()) {
                    for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                        orderMaintenanceItem.setStatus(EStatusOrderServiceDetail.getEStatusOrderServiceDetailByName(orderMaintenanceItemUpdateReqDTO.getStatus()));
                    }

                    orderMaintenanceItemRepository.saveAll(orderMaintenanceItemList);
                    List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = new ArrayList<>();

                    for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                        orderMaintenanceItemResDTOList.add(orderMaintenanceItem.toOrderMaintenanceItemResDTO());
                    }

                    return orderMaintenanceItemResDTOList;
                }
            }
        }

        return null;
    }

    @Override
    public List<OrderMaintenanceItemResDTO> updateStatusDone(OrderMaintenanceItemUpdateStatusReqDTO orderMaintenanceItemUpdateStatusReqDTO) {
        Optional<OrderService> orderServiceOptional = orderServiceRepository.findOrderServiceByIdAndStatus(Long.parseLong(orderMaintenanceItemUpdateStatusReqDTO.getOrderServiceId()), EStatusOrderService.STATUS_WAITING_PAYMENT);

        if (orderServiceOptional.isPresent()) {
            Optional<OrderMaintenance> orderMaintenanceOptional = orderMaintenanceRepository.findOrderMaintenanceByOrderService(orderServiceOptional.get());

            if (orderMaintenanceOptional.isPresent()) {
                Optional<ServiceArea> serviceAreaOptional = serviceAreaRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderMaintenanceItemUpdateStatusReqDTO.getServiceAreaId()));

                if (serviceAreaOptional.isPresent()) {
                    List<OrderMaintenanceItem> orderMaintenanceItemList = orderMaintenanceItemRepository.findAllByOrderMaintenanceAndMaintenanceItemServiceArea(orderMaintenanceOptional.get(), serviceAreaOptional.get());

                    if (!orderMaintenanceItemList.isEmpty()) {
                        for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                            if (orderMaintenanceItem.getId() == Long.parseLong(orderMaintenanceItemUpdateStatusReqDTO.getMaintenanceItemId())) {
                                orderMaintenanceItem.setStatus(EStatusOrderServiceDetail.getEStatusOrderServiceDetailByName(orderMaintenanceItemUpdateStatusReqDTO.getStatus()));
                            }
                        }

                        orderMaintenanceItemRepository.saveAll(orderMaintenanceItemList);
                        List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = new ArrayList<>();

                        for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                            orderMaintenanceItemResDTOList.add(orderMaintenanceItem.toOrderMaintenanceItemResDTO());
                        }

                        for (OrderMaintenanceItemResDTO orderMaintenanceItemResDTO : orderMaintenanceItemResDTOList) {
                            List<OrderMaintenanceItemAccessoryResDTO> orderMaintenanceItemAccessoryResDTOList = orderMaintenanceItemAccessoryRepository.findAllOrderMaintenanceItemAccessoryResDTOByOrderMaintenanceItemId(orderMaintenanceItemResDTO.getId());
                            orderMaintenanceItemResDTO.setOrderMaintenanceItemAccessories(orderMaintenanceItemAccessoryResDTOList);
                        }

                        return orderMaintenanceItemResDTOList;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public List<OrderMaintenanceItemResDTO> updateStatusDoneAll(OrderMaintenanceItemUpdateStatusReqDTO orderMaintenanceItemUpdateStatusReqDTO) {
        Optional<OrderService> orderServiceOptional = orderServiceRepository.findOrderServiceByIdAndStatus(Long.parseLong(orderMaintenanceItemUpdateStatusReqDTO.getOrderServiceId()), EStatusOrderService.STATUS_WAITING_PAYMENT);

        if (orderServiceOptional.isPresent()) {
            Optional<OrderMaintenance> orderMaintenanceOptional = orderMaintenanceRepository.findOrderMaintenanceByOrderService(orderServiceOptional.get());

            if (orderMaintenanceOptional.isPresent()) {
                Optional<ServiceArea> serviceAreaOptional = serviceAreaRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderMaintenanceItemUpdateStatusReqDTO.getServiceAreaId()));

                if (serviceAreaOptional.isPresent()) {
                    List<OrderMaintenanceItem> orderMaintenanceItemList = orderMaintenanceItemRepository.findAllByOrderMaintenanceAndMaintenanceItemServiceArea(orderMaintenanceOptional.get(), serviceAreaOptional.get());

                    if (!orderMaintenanceItemList.isEmpty()) {
                        for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                            orderMaintenanceItem.setStatus(EStatusOrderServiceDetail.getEStatusOrderServiceDetailByName(orderMaintenanceItemUpdateStatusReqDTO.getStatus()));
                        }

                        orderMaintenanceItemRepository.saveAll(orderMaintenanceItemList);
                        List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = new ArrayList<>();

                        for (OrderMaintenanceItem orderMaintenanceItem : orderMaintenanceItemList) {
                            orderMaintenanceItemResDTOList.add(orderMaintenanceItem.toOrderMaintenanceItemResDTO());
                        }

                        for (OrderMaintenanceItemResDTO orderMaintenanceItemResDTO : orderMaintenanceItemResDTOList) {
                            List<OrderMaintenanceItemAccessoryResDTO> orderMaintenanceItemAccessoryResDTOList = orderMaintenanceItemAccessoryRepository.findAllOrderMaintenanceItemAccessoryResDTOByOrderMaintenanceItemId(orderMaintenanceItemResDTO.getId());
                            orderMaintenanceItemResDTO.setOrderMaintenanceItemAccessories(orderMaintenanceItemAccessoryResDTOList);
                        }

                        return orderMaintenanceItemResDTOList;
                    }
                }
            }
        }
        return null;
    }

}
