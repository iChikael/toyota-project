package com.cg.domain.entity.storage;

import com.cg.domain.dto.accessory.AccessoryCreateResDTO;
import com.cg.domain.dto.accessory.AccessoryReceptionResDTO;
import com.cg.domain.dto.accessory.AccessoryResDTO;
import com.cg.domain.dto.accessory.AccessoryUpdateResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.enums.EUnit;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Table(name = "accessories")
public class Accessory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(precision = 10, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EUnit unit;


    @ManyToOne
    @JoinColumn(name = "accessory_role_id", referencedColumnName = "id", nullable = false)
    private AccessoryRole accessoryRole;


    public AccessoryCreateResDTO toAccessoryCreateResDTO() {
        return new AccessoryCreateResDTO()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity)
                .setUnit(unit.getValue())
                .setAccessoryRole(accessoryRole.toAccessoryRoleResDTO().getCodeName())
                ;
    }

    public AccessoryUpdateResDTO toAccessoryUpdateResDTO() {
        return new AccessoryUpdateResDTO()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity)
                .setUnit(unit.getValue())
                .setAccessoryRole(accessoryRole.toAccessoryRoleResDTO().getCodeName());
    }

    public AccessoryResDTO toAccessoryResDTO() {
        return new AccessoryResDTO()
                .setId(id)
                .setName(name)
                .setCode(code)
                .setPrice(price)
                .setQuantity(quantity)
                .setUnit(unit.getValue())
                .setAccessoryRole(accessoryRole.toAccessoryRoleResDTO().getCodeName())
                ;

    }

    public AccessoryReceptionResDTO toAccessoryReceptionResDTO() {
        return new AccessoryReceptionResDTO()
                .setId(id)
                .setName(name)
                .setCode(code)
                .setPrice(price)
                .setUnit(unit.getValue())
                .setAccessoryRole(accessoryRole.toAccessoryRoleResDTO().getCodeName())
                ;

    }


}
