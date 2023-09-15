package com.cg.service.bill;

import com.cg.domain.entity.bill.BillCar;
import com.cg.repository.bill.IBillCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillCarServiceImpl implements IBillCarService {

    @Autowired
    private IBillCarRepository billCarRepository;

    @Override
    public List<BillCar> findAll() {
        return billCarRepository.findAll();
    }

    @Override
    public Optional<BillCar> findById(Long id) {
        return billCarRepository.findById(id);
    }

    @Override
    public BillCar save(BillCar billCar) {
        return billCarRepository.save(billCar);
    }

    @Override
    public void delete(BillCar billCar) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
