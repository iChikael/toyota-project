package com.cg.repository.customer;

import com.cg.domain.dto.customer.CustomerResDTO;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, String> {

    @Query("SELECT NEW com.cg.domain.dto.customer.CustomerResDTO ( " +
                "cus.id, " +
                "cus.fullName, " +
                "cus.identificationNumber, " +
                "cus.email, " +
                "cus.dob, " +
                "cus.phone, " +
                "cus.createdAt, " +
                "cus.customerLocation" +
            ") " +
            "FROM Customer as cus " +
            "WHERE cus.deleted = false " +
            "ORDER BY cus.createdAt DESC"
    )
    List<CustomerResDTO> findAllCustomerResDTO();

    @Query("SELECT NEW com.cg.domain.dto.customer.CustomerResDTO ( " +
                "cus.id, " +
                "cus.fullName, " +
                "cus.identificationNumber, " +
                "cus.email, " +
                "cus.dob, " +
                "cus.phone, " +
                "cus.createdAt, " +
                "cus.customerLocation" +
            ") " +
            "FROM Customer as cus " +
            "WHERE cus.deleted = false " +
            "AND cus.phone LIKE %?1%"
    )
    List<CustomerResDTO> findAllCustomerResDTOByKeyWord(String phone);

    @Query("SELECT NEW com.cg.domain.dto.customer.CustomerResDTO ( " +
                "cus.id, " +
                "cus.fullName, " +
                "cus.identificationNumber, " +
                "cus.email, " +
                "cus.dob, " +
                "cus.phone, " +
                "cus.createdAt, " +
                "cus.customerLocation" +
            ") " +
            "FROM Customer as cus " +
            "WHERE cus.deleted = false " +
            "AND cus.id = :id"
    )
    Optional<CustomerResDTO> findCustomerByIdResDTO(@Param("id") String id);

    Optional<Customer> findByUser(User user);
}
