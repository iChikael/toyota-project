package com.cg.domain.entity.orderService;

import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemResDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.enums.EChecked;
import com.cg.domain.enums.EMaintenanceChecklist;
import com.cg.domain.enums.EStatusOrderServiceDetail;
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
@Table(name = "order_maintenance_item")
public class OrderMaintenanceItem extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "maintenance_item_name", nullable = false)
    private String serviceItemName;

    @Enumerated(EnumType.STRING)
    @Column(name = "maintenance_checklist_name", length = 50)
    private EMaintenanceChecklist checklistName;

    @Enumerated(EnumType.STRING)
    @Column(name = "check_name", length = 50)
    private EChecked checkName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name", length = 50)
    private EStatusOrderServiceDetail status;

    @OneToOne
    private MaintenanceItem maintenanceItem;

    @ManyToOne
    @JoinColumn(name = "order_maintenance_id", referencedColumnName = "id", nullable = false)
    private OrderMaintenance orderMaintenance;

    public OrderMaintenanceItemResDTO toOrderMaintenanceItemResDTO () {
        return new OrderMaintenanceItemResDTO()
                .setId(id)
                .setServiceItemName(serviceItemName)
                .setChecklistName(checklistName.getValue())
                .setStatus(status.getValue())
                .setMaintenanceItem(maintenanceItem.toMaintenanceItemResDTO());
    }

}
