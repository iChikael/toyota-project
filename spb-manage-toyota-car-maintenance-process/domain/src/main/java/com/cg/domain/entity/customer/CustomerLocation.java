package com.cg.domain.entity.customer;

import com.cg.domain.dto.customer.CustomerLocationResDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer_location")
public class CustomerLocation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province_id", nullable = false)
    private String provinceId;

    @Column(name = "province_name", nullable = false)
    private String provinceName;

    @Column(name = "district_id", nullable = false)
    private String districtId;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    @Column(name = "ward_id", nullable = false)
    private String wardId;

    @Column(name = "ward_name", nullable = false)
    private String wardName;

    private String address;

    public CustomerLocationResDTO toCustomerLocationResDTO() {
        return new CustomerLocationResDTO()
                .setId(id)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address);
    }
}
