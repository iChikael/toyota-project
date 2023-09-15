package com.cg.service.bill;

import com.cg.domain.dto.service.billService.BillServiceDetailAccessoryResDTO;
import com.cg.domain.entity.bill.BillServiceDetailAccessory;
import com.cg.repository.bill.IBillServiceDetailAccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillServiceDetailAccessoryServiceImpl implements IBillServiceDetailAccessoryService {

    @Autowired
    private IBillServiceDetailAccessoryRepository billServiceDetailAccessoryRepository;

    @Override
    public List<BillServiceDetailAccessory> findAll() {
        return billServiceDetailAccessoryRepository.findAll();
    }

    @Override
    public Optional<BillServiceDetailAccessory> findById(Long id) {
        return billServiceDetailAccessoryRepository.findById(id);
    }

    @Override
    public BillServiceDetailAccessory save(BillServiceDetailAccessory billServiceDetailAccessory) {
        return billServiceDetailAccessoryRepository.save(billServiceDetailAccessory);
    }

    @Override
    public void delete(BillServiceDetailAccessory billServiceDetailAccessory) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<BillServiceDetailAccessoryResDTO> findBillServiceDetailAccessoryResDTOByBillServiceDetailId(Long billServiceDetailId) {
        return billServiceDetailAccessoryRepository.findBillServiceDetailAccessoryResDTOByBillServiceDetailId(billServiceDetailId);
    }
}
