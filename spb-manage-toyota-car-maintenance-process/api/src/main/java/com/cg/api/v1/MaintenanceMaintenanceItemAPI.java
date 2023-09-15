package com.cg.api.v1;

import com.cg.domain.dto.service.maintenanceMaintenanceItem.MaintenanceMaintenanceItemCreateReqDTO;
import com.cg.domain.dto.service.maintenanceMaintenanceItem.MaintenanceMaintenanceItemResDTO;
import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateResDTO;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.entity.service.maintenance.MaintenanceMaintenanceItem;
import com.cg.domain.enums.EMaintenanceChecklist;
import com.cg.exception.DataInputException;
import com.cg.service.service.maintenance.IMaintenanceItemService;
import com.cg.service.service.maintenance.IMaintenanceMaintenanceItemService;
import com.cg.service.service.maintenance.IMaintenanceService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/maintenance-maintenance-items")
public class MaintenanceMaintenanceItemAPI {

    @Autowired
    private IMaintenanceService maintenanceService;

    @Autowired
    private IMaintenanceItemService maintenanceItemService;

    @Autowired
    private IMaintenanceMaintenanceItemService maintenanceMaintenanceItemService;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping("/{maintenanceId}")
    public ResponseEntity<List<?>> getAllMaintenanceMaintenanceItemResDTO(@PathVariable String maintenanceId) {
        if (!validateUtils.isNumberValid(maintenanceId)) {
            throw new DataInputException("Mã hạng mục bảo dưỡng không hợp lệ");
        }

        List<MaintenanceMaintenanceItemResDTO> maintenanceMaintenanceItemList = maintenanceMaintenanceItemService.findAllByMaintenance_Id(Long.parseLong(maintenanceId));

        if (maintenanceMaintenanceItemList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(maintenanceMaintenanceItemList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createMaintenanceServiceItem(@RequestBody MaintenanceMaintenanceItemCreateReqDTO maintenanceMaintenanceItemCreateReqDTO, BindingResult bindingResult) {

        new MaintenanceMaintenanceItemCreateReqDTO().validate(maintenanceMaintenanceItemCreateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(maintenanceMaintenanceItemCreateReqDTO.getMaintenanceItemId())) {
            throw new DataInputException("Mã hạng mục bảo dưỡng không hợp lệ");
        }

        if (!validateUtils.isNumberValid(maintenanceMaintenanceItemCreateReqDTO.getMaintenanceId())) {
            throw new DataInputException("Mã gói bảo dưỡng không hợp lệ");
        }

        Optional<MaintenanceItem> maintenanceItemOptional = maintenanceItemService.findByIdAndDeletedIsFalse(Long.parseLong(maintenanceMaintenanceItemCreateReqDTO.getMaintenanceItemId()));

        if (maintenanceItemOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy hạng mục bảo dưỡng!");
        }

        Optional<Maintenance> maintenanceOptional = maintenanceService.findByIdAndDeletedIsFalse(Long.parseLong(maintenanceMaintenanceItemCreateReqDTO.getMaintenanceId()));

        if (maintenanceOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy gói bảo dưỡng!");
        }

        MaintenanceMaintenanceItem maintenanceMaintenanceItem = new MaintenanceMaintenanceItem();

        maintenanceMaintenanceItem.setId(null);
        maintenanceMaintenanceItem.setMaintenanceItem(maintenanceItemOptional.get());
        maintenanceMaintenanceItem.setMaintenance(maintenanceOptional.get());
        maintenanceMaintenanceItem.setName(EMaintenanceChecklist.getEMaintenanceChecklistByName(maintenanceMaintenanceItemCreateReqDTO.getStatus()));

        maintenanceMaintenanceItemService.save(maintenanceMaintenanceItem);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
