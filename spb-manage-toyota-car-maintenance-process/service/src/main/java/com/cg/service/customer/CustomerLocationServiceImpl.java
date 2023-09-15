package com.cg.service.customer;

import com.cg.domain.entity.customer.CustomerLocation;
import com.cg.repository.customer.ICustomerLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerLocationServiceImpl implements ICustomerLocationService {

    @Autowired
    private ICustomerLocationRepository customerLocationRepository;

    @Override
    public List<CustomerLocation> findAll() {
        return customerLocationRepository.findAll();
    }

    @Override
    public Optional<CustomerLocation> findById(Long id) {
        return customerLocationRepository.findById(id);
    }

    @Override
    public CustomerLocation save(CustomerLocation customerLocation) {
        return customerLocationRepository.save(customerLocation);
    }

    @Override
    public void delete(CustomerLocation customerLocation) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
