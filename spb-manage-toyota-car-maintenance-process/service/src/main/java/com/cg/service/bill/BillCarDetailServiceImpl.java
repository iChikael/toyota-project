package com.cg.service.bill;

import com.cg.domain.entity.bill.BillCarDetail;
import com.cg.repository.bill.IBillCarDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillCarDetailServiceImpl implements IBillCarDetailService {

    @Autowired
    private IBillCarDetailRepository billCarDetailRepository;

    @Override
    public List<BillCarDetail> findAll() {
        return billCarDetailRepository.findAll();
    }

    @Override
    public Optional<BillCarDetail> findById(Long id) {
        return billCarDetailRepository.findById(id);
    }

    @Override
    public BillCarDetail save(BillCarDetail billCarDetail) {
        return billCarDetailRepository.save(billCarDetail);
    }

    @Override
    public void delete(BillCarDetail billCarDetail) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
