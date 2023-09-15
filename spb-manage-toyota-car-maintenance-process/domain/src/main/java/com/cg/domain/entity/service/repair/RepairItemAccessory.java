package com.cg.domain.entity.service.repair;

import com.cg.domain.dto.service.repairItemAccessory.RepairItemAccessoryCreateResDTO;
import com.cg.domain.dto.service.repairItemAccessory.RepairItemAccessoryResDTO;
import com.cg.domain.dto.service.repairItemAccessory.RepairItemAccessoryUpdateResDTO;
import com.cg.domain.entity.storage.Accessory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "repair_item_accessories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"repair_item_id", "accessory_id"}, name = "UK_repair_item_accessory")
})
public class RepairItemAccessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accessory_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal accessoryQuantity;

    @ManyToOne
    @JoinColumn(name = "repair_item_id", referencedColumnName = "id")
    private RepairItem repairItem;

    @ManyToOne
    @JoinColumn(name = "accessory_id", referencedColumnName = "id")
    private Accessory accessory;


    public RepairItemAccessoryResDTO toRepairItemAccessoryResDTO() {
        return new RepairItemAccessoryResDTO()
                .setAccessory(accessory.toAccessoryReceptionResDTO())
                .setRepairItem(repairItem.toRepairItemResDTO())
                ;
    }

    public RepairItemAccessoryCreateResDTO toRepairItemAccessoryCreateResDTO() {
        return new RepairItemAccessoryCreateResDTO()
                .setAccessory(accessory.toAccessoryResDTO())
                .setRepairItem(repairItem.toRepairItemResDTO())
                ;
    }

    public RepairItemAccessoryUpdateResDTO toRepairItemAccessoryUpdateResDTO() {
        return new RepairItemAccessoryUpdateResDTO()
                .setAccessory(accessory.toAccessoryResDTO())
                .setRepairItem(repairItem.toRepairItemResDTO())
                ;
    }

}
