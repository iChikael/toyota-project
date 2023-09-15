package com.cg.api.v1;


import com.cg.domain.dto.service.maintenanceItemAccessory.MaintenanceItemAccessoryCreateReqDTO;
import com.cg.domain.dto.service.maintenanceItemAccessory.MaintenanceItemAccessoryResDTO;
import com.cg.domain.dto.service.maintenanceMaintenanceItem.MaintenanceMaintenanceItemResDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.entity.service.maintenance.MaintenanceItemAccessory;
import com.cg.domain.entity.storage.Accessory;
import com.cg.exception.DataInputException;
import com.cg.service.service.maintenance.IMaintenanceItemAccessoryService;
import com.cg.service.service.maintenance.IMaintenanceItemService;
import com.cg.service.storage.IAccessoryService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/maintenance-item-accessories")
public class MaintenanceItemAccessoryAPI {

    @Autowired
    private IMaintenanceItemService maintenanceItemService;

    @Autowired
    private IAccessoryService accessoryService;

    @Autowired
    private IMaintenanceItemAccessoryService maintenanceItemAccessoryService;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping("/{maintenanceItemId}")
    public ResponseEntity<List<?>> getAllMaintenanceItemAccessoryResDTO(@PathVariable String maintenanceItemId) {
        if (!validateUtils.isNumberValid(maintenanceItemId)) {
            throw new DataInputException("Mã hạng mục bảo dưỡng không hợp lệ");
        }

        List<MaintenanceItemAccessoryResDTO> maintenanceItemAccessoryResDTOList = maintenanceItemAccessoryService.findAllByMaintenanceItem_Id(Long.parseLong(maintenanceItemId));

        if (maintenanceItemAccessoryResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(maintenanceItemAccessoryResDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MaintenanceItemAccessoryCreateReqDTO maintenanceItemAccessoryCreateReqDTO, BindingResult bindingResult) {

        new MaintenanceItemAccessoryCreateReqDTO().validate(maintenanceItemAccessoryCreateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(maintenanceItemAccessoryCreateReqDTO.getMaintenanceItemId())) {
            throw new DataInputException("Mã hạng mục bảo dưỡng không hợp lệ");
        }

        if (!validateUtils.isNumberValid(maintenanceItemAccessoryCreateReqDTO.getAccessoryId())) {
            throw new DataInputException("Mã phụ kiện không hợp lệ");
        }

        Optional<MaintenanceItem> maintenanceItemOptional = maintenanceItemService.findByIdAndDeletedIsFalse(Long.parseLong(maintenanceItemAccessoryCreateReqDTO.getMaintenanceItemId()));

        if (maintenanceItemOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy hạng mục bảo dưỡng!");
        }

        Optional<Accessory> accessoryOptional = accessoryService.findByIdAndDeletedIsFalse(Long.parseLong(maintenanceItemAccessoryCreateReqDTO.getAccessoryId()));

        if (accessoryOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy phụ kiện!");

        }

        MaintenanceItemAccessory maintenanceItemAccessory = new MaintenanceItemAccessory();

        maintenanceItemAccessory.setId(null);
        maintenanceItemAccessory.setMaintenanceItem(maintenanceItemOptional.get());
        maintenanceItemAccessory.setAccessory(accessoryOptional.get());

        maintenanceItemAccessoryService.save(maintenanceItemAccessory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/import-to-db")
    public ResponseEntity<?> importAccessoriesFromExcelToDb(@RequestPart MultipartFile file) {

        maintenanceItemAccessoryService.importToDb(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
