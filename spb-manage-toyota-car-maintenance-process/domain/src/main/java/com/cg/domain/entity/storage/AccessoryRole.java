package com.cg.domain.entity.storage;

import com.cg.domain.dto.accessory.AccessoryRoleResDTO;
import com.cg.domain.enums.EAccessoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@PersistenceContext
@Entity
@Table(name = "accessory_role")
public class AccessoryRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EAccessoryType name;

    @OneToMany(mappedBy = "accessoryRole")
    private List<Accessory> accessories;


    public AccessoryRoleResDTO toAccessoryRoleResDTO() {
        return new AccessoryRoleResDTO()
                .setId(id)
                .setCode(code)
                .setCodeName(name.getValue())
                ;
    }


}
