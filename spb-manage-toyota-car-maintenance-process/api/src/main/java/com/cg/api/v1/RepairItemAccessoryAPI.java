package com.cg.api.v1;

import com.cg.domain.dto.service.repairItemAccessory.RepairItemAccessoryCreateReqDTO;
import com.cg.domain.dto.service.repairItemAccessory.RepairItemAccessoryResDTO;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.domain.entity.service.repair.RepairItemAccessory;
import com.cg.domain.entity.storage.Accessory;
import com.cg.exception.DataInputException;

import com.cg.service.service.repair.IRepairItemService;
import com.cg.service.service.repairItemAccessory.IRepairItemAccessoryService;
import com.cg.service.storage.IAccessoryService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/repair-item-accessories")
public class RepairItemAccessoryAPI {

    @Autowired
    private IAccessoryService accessoryService;

    @Autowired
    private IRepairItemAccessoryService repairItemAccessoryService;

    @Autowired
    private IRepairItemService repairItemService;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping("/{repairItemId}")
    public ResponseEntity<List<?>> getAllRepairItemAccessoryResDTO (@PathVariable String repairItemId) {
        if (!validateUtils.isNumberValid(repairItemId)) {
            throw new DataInputException("Mã hạng mục sửa chữa không hợp lệ");
        }

        List<RepairItemAccessoryResDTO> repairItemAccessoryResDTOList = repairItemAccessoryService.findAllByRepairItemId(Long.parseLong(repairItemId));

        if (repairItemAccessoryResDTOList.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(repairItemAccessoryResDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRepairItemAccessory(@RequestBody RepairItemAccessoryCreateReqDTO repairItemAccessoryCreateReqDTO) {

        Optional<RepairItem> serviceItemOptional = repairItemService.findById(Long.parseLong(repairItemAccessoryCreateReqDTO.getRepairItemId()));

        if (serviceItemOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin dịch vụ!");
        }

        Optional<Accessory> accessoryOptional = accessoryService.findById(Long.parseLong(repairItemAccessoryCreateReqDTO.getAccessoryId()));

        if (accessoryOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy phụ kiện!");
        }

        RepairItemAccessory repairItemAccessory = new RepairItemAccessory();

        repairItemAccessory.setId(null);
        repairItemAccessory.setRepairItem(serviceItemOptional.get());
        repairItemAccessory.setAccessory(accessoryOptional.get());

        repairItemAccessoryService.save(repairItemAccessory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
