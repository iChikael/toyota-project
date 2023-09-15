package com.cg.service.customer;

import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.customer.CustomerAvatar;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ICustomerAvatarService extends IGeneralService<CustomerAvatar, String> {
    CustomerAvatar createCustomerAvatar(MultipartFile avatar, Customer customer);

    Optional<CustomerAvatar> findCustomerAvatarByCustomerIdAndDeletedIsFalse(String id);
}
