package com.cg.domain.entity.storage;

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
@Table(name = "suppliers")
public class Supplier extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_code", nullable = false)
    private String supplierCode;

    @Column(name = "supplier_name", nullable = false)
    private String supplierName;


    @Column(name = "bank_number", nullable = false)
    private Long bankNumber;

    @OneToOne
    @JoinColumn(name = "supplier_location_id", referencedColumnName = "id", nullable = false)
    private SupplierLocation supplierLocation;
}
