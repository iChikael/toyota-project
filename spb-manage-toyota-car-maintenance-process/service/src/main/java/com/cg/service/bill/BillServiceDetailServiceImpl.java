package com.cg.service.bill;

import com.cg.domain.dto.service.billService.BillServiceDetailResDTO;
import com.cg.domain.entity.bill.BillServiceDetail;
import com.cg.repository.bill.IBillServiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillServiceDetailServiceImpl implements IBillServiceDetailService {

    @Autowired
    private IBillServiceDetailRepository billServiceDetailRepository;

    @Override
    public List<BillServiceDetail> findAll() {
        return billServiceDetailRepository.findAll();
    }

    @Override
    public Optional<BillServiceDetail> findById(Long id) {
        return billServiceDetailRepository.findById(id);
    }

    @Override
    public BillServiceDetail save(BillServiceDetail billServiceDetail) {
        return billServiceDetailRepository.save(billServiceDetail);
    }

    @Override
    public void delete(BillServiceDetail billServiceDetail) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<BillServiceDetailResDTO> findAllBillServiceDetailResDTOByBillServiceId(Long billServiceId) {
        return billServiceDetailRepository.findAllBillServiceDetailResDTOByBillServiceId(billServiceId);
    }
}
