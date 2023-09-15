package com.cg.repository.customer;

import com.cg.domain.entity.customer.CustomerLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerLocationRepository extends JpaRepository<CustomerLocation, Long> {
}
