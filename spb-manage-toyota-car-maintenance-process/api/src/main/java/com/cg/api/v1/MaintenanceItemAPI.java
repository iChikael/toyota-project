package com.cg.api.v1;


import com.cg.domain.dto.service.maintenanceItem.*;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.exception.DataInputException;
import com.cg.service.service.IServiceAreaService;
import com.cg.service.service.maintenance.IMaintenanceItemService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/maintenance-items")
public class MaintenanceItemAPI {

    @Autowired
    private IMaintenanceItemService maintenanceItemService;

    @Autowired
    private IServiceAreaService serviceAreaService;

    @Autowired
    private ValidateUtils validateUtils;


    @GetMapping
    public ResponseEntity<?> getAll() {
        List<MaintenanceItemResDTO> maintenanceItemResDTOS = maintenanceItemService.findAllMaintenanceItemResDTO();

        if (maintenanceItemResDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(maintenanceItemResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã hạng mục bảo dưỡng không hợp lệ");
        }

        Long maintenanceItemId = Long.parseLong(id);

        Optional<MaintenanceItem> maintenanceItemOptional = maintenanceItemService.findByIdAndDeletedIsFalse(maintenanceItemId);

        if (maintenanceItemOptional.isEmpty()) {
            throw new DataInputException("Hạng mục bảo dưỡng không tồn tại !");
        }

        MaintenanceItem maintenanceItem = maintenanceItemOptional.get();

        MaintenanceItemResDTO maintenanceItemResDTO = maintenanceItem.toMaintenanceItemResDTO();

        return new ResponseEntity<>(maintenanceItemResDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/export-to-excel")
    public ResponseEntity<StreamingResponseBody> exportMaintenancesFromDBtoExCel(HttpServletResponse response) {

        StreamingResponseBody responseBody = maintenanceItemService.exportToExcel(response);

        return ResponseEntity.ok(responseBody);

    }

    @PostMapping()
    public ResponseEntity<?> create(@ModelAttribute MaintenanceItemCreateReqDTO maintenanceItemCreateReqDTO, BindingResult bindingResult) {

        new MaintenanceItemCreateReqDTO().validate(maintenanceItemCreateReqDTO, bindingResult);

        ServiceArea serviceArea = serviceAreaService.findByIdAndDeletedIsFalse(Long.valueOf(maintenanceItemCreateReqDTO.getServiceAreaId())).orElseThrow(() -> {
            throw new DataInputException("Khu vực dịch vụ không tồn tại");
        });

        MaintenanceItem maintenanceItem = maintenanceItemService.create(maintenanceItemCreateReqDTO, serviceArea);

        MaintenanceItemCreateResDTO maintenanceItemCreateResDTO = maintenanceItem.toMaintenanceItemCreateResDTO();

        return new ResponseEntity<>(maintenanceItemCreateResDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = "/import-to-db")
    public ResponseEntity<?> importMaintenancesFromExcelToDb(@RequestPart MultipartFile file) {

        maintenanceItemService.importToDb(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @ModelAttribute MaintenanceItemUpdateReqDTO maintenanceItemUpdateReqDTO, BindingResult bindingResult) {

        new MaintenanceItemUpdateReqDTO().validate(maintenanceItemUpdateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã hạng mục bảo dưỡng không hợp lệ");
        }

        Long maintenanceItemId = Long.parseLong(id);

        Optional<MaintenanceItem> maintenanceItemOptional = maintenanceItemService.findByIdAndDeletedIsFalse(maintenanceItemId);

        ServiceArea serviceArea = serviceAreaService.findByIdAndDeletedIsFalse(Long.valueOf(maintenanceItemUpdateReqDTO.getServiceAreaId())).orElseThrow(() -> {
            throw new DataInputException("Khu vực dịch vụ không tồn tại");
        });

        if (maintenanceItemOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin hạng mục bảo dưỡng!");
        }

        MaintenanceItem maintenanceItem = maintenanceItemService.update(maintenanceItemOptional.get(), maintenanceItemUpdateReqDTO, serviceArea);

        MaintenanceItemUpdateResDTO maintenanceItemUpdateResDTO = maintenanceItem.toMaintenanceItemUpdateResDTO();

        return new ResponseEntity<>(maintenanceItemUpdateResDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã hạng mục bảo dưỡng không hợp lệ");
        }

        Long maintenanceItemId = Long.parseLong(id);

        Optional<MaintenanceItem> maintenanceItemOptional = maintenanceItemService.findByIdAndDeletedIsFalse(maintenanceItemId);

        if (maintenanceItemOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin hạng mục bảo dưỡng!");
        }

        MaintenanceItem maintenanceItem = maintenanceItemOptional.get();

        maintenanceItem.setDeleted(true);

        maintenanceItemService.save(maintenanceItem);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
