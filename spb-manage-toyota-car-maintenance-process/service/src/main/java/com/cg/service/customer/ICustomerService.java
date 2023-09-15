package com.cg.service.customer;

import com.cg.domain.dto.customer.CustomerResDTO;
import com.cg.domain.dto.customer.CustomerUpdateInfoReqDTO;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.user.User;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


public interface ICustomerService extends IGeneralService<Customer, String> {

    List<CustomerResDTO> findAllCustomerResDTO();
    List<CustomerResDTO> findAllCustomerResDTOByKeyWord(String phone);

    Customer updateCustomer(Customer customer, CustomerUpdateInfoReqDTO customerUpdateInfoReqDTO);

    Optional<Customer> findByUser(User currentUser);

    Optional<CustomerResDTO> findCustomerByIdResDTO(String username);

    void importToDb(MultipartFile multipartfile);

    StreamingResponseBody exportToExcel(HttpServletResponse response);
}
