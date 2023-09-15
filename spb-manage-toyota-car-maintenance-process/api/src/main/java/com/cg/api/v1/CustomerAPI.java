package com.cg.api.v1;

import com.cg.domain.dto.customer.CustomerResDTO;
import com.cg.domain.dto.customer.CustomerUpdatePasswordReqDTO;
import com.cg.domain.dto.customer.CustomerUpdateInfoReqDTO;
import com.cg.domain.dto.customer.CustomerUpdateInfoResDTO;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.customer.CustomerAvatar;
import com.cg.domain.entity.user.User;
import com.cg.exception.DataInputException;
import com.cg.service.customer.ICustomerAvatarService;
import com.cg.service.customer.ICustomerService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ICustomerAvatarService customerAvatarService;

    @Autowired
    private IUserService userService;


    @GetMapping
    public ResponseEntity<List<?>> getAllCustomerDTO() {
        List<CustomerResDTO> customerResDTOList = new ArrayList<>();

        List<CustomerResDTO> customerResDTOS = customerService.findAllCustomerResDTO();

        if (customerResDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (CustomerResDTO customerResDTO : customerResDTOS) {
            Optional<CustomerAvatar> customerAvatar = customerAvatarService.findCustomerAvatarByCustomerIdAndDeletedIsFalse(customerResDTO.getId());

            customerAvatar.ifPresent(avatar -> customerResDTO.setCustomerAvatarResDTO(avatar.toCustomerAvatarResDTO()));

            customerResDTOList.add(customerResDTO);
        }

        return new ResponseEntity<>(customerResDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllCustomerByIdDTO(@PathVariable String id) {
        Optional<CustomerResDTO> customerResDTOOptional = customerService.findCustomerByIdResDTO(id);

        if (customerResDTOOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CustomerResDTO customerResDTO = customerResDTOOptional.get();

        Optional<CustomerAvatar> customerAvatarOptional = customerAvatarService.findCustomerAvatarByCustomerIdAndDeletedIsFalse(customerResDTO.getId());

        customerAvatarOptional.ifPresent(customerAvatar -> customerResDTO.setCustomerAvatarResDTO(customerAvatar.toCustomerAvatarResDTO()));

        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<?>> search(@RequestParam(name = "phone", required = false) String phone) {

        if (phone.length() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CustomerResDTO> customerResDTOS = customerService.findAllCustomerResDTOByKeyWord(phone);

        if (customerResDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customerResDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "/export-to-excel")
    public ResponseEntity<StreamingResponseBody> exportCustomersFromDBtoExCel(HttpServletResponse response) {

        StreamingResponseBody responseBody = customerService.exportToExcel(response);

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping(path = "/import-to-db")
    public ResponseEntity<?> importCustomersFromExcelToDb(@RequestPart MultipartFile file) {

        customerService.importToDb(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/update-password/{id}")
    public ResponseEntity<?> updatePasswordCustomer(@PathVariable String id, @RequestBody CustomerUpdatePasswordReqDTO customerUpdatePasswordReqDTO, BindingResult bindingResult) {

        new CustomerUpdatePasswordReqDTO().validate(customerUpdatePasswordReqDTO, bindingResult);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (customerOptional.isPresent()) {

            User user = userService.getByUsername(customerOptional.get().getUser().getUsername());

            user.setPassword(customerUpdatePasswordReqDTO.getPassword());

            userService.save(user);

            CustomerResDTO customerResDTO = customerOptional.get().toCustomerResDTO();

            CustomerAvatar customerAvatar = customerAvatarService.findCustomerAvatarByCustomerIdAndDeletedIsFalse(customerOptional.get().getId()).get();

            customerResDTO.setCustomerAvatarResDTO(customerAvatar.toCustomerAvatarResDTO());

            return new ResponseEntity<>(customerResDTO, HttpStatus.OK);

        } else {
            throw new DataInputException("Không tìm thấy thông tin khách hàng!");
        }
    }

    @PatchMapping("/update-info/{id}")
    public ResponseEntity<?> updateInfoCustomer(@PathVariable String id,
                                                @ModelAttribute CustomerUpdateInfoReqDTO customerUpdateInfoReqDTO,
                                                MultipartFile customerAvatar,
                                                BindingResult bindingResult) {

        new CustomerUpdateInfoReqDTO().validate(customerUpdateInfoReqDTO, bindingResult);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (customerOptional.isPresent()) {

            Customer customer = customerService.updateCustomer(customerOptional.get(), customerUpdateInfoReqDTO);

            CustomerAvatar customerAvatarNew;

            if (customerAvatar != null && customerAvatar.getSize() != 0) {

                Optional<CustomerAvatar> customerAvatarOptional = customerAvatarService.findCustomerAvatarByCustomerIdAndDeletedIsFalse(customer.getId());

                customerAvatarOptional.get().setDeleted(true);

                customerAvatarNew = customerAvatarService.createCustomerAvatar(customerAvatar, customer);

            } else {

                Optional<CustomerAvatar> customerAvatarOptional = customerAvatarService.findCustomerAvatarByCustomerIdAndDeletedIsFalse(customer.getId());

                customerAvatarNew = customerAvatarOptional.get().setCustomer(customer);

            }
            CustomerUpdateInfoResDTO customerUpdateInfoResDTO = customer.toCustomerUpdateInfoResDTO();

            customerUpdateInfoResDTO.setCustomerAvatarResDTO(customerAvatarNew.toCustomerAvatarResDTO());

            return new ResponseEntity<>(customerUpdateInfoResDTO, HttpStatus.OK);
        } else {
            throw new DataInputException("Không tìm thấy thông tin khách hàng!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (customerOptional.isPresent()) {

            Customer customer = customerOptional.get();

            customerService.delete(customer);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new DataInputException("Không tìm thấy thông tin khách hàng!");
        }
    }
}
