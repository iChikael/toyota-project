package com.cg.repository.customer;

import com.cg.domain.entity.customer.CustomerAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerAvatarRepository extends JpaRepository<CustomerAvatar, String> {

    Optional<CustomerAvatar> findCustomerAvatarByCustomerIdAndDeletedIsFalse(String id);
}
