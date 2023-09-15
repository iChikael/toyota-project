package com.cg.api.v1;

import com.cg.domain.dto.service.maintaince.*;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.exception.DataInputException;
import com.cg.service.service.maintenance.IMaintenanceService;
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
@RequestMapping("/api/v1/maintenances")
public class MaintenanceAPI {

    @Autowired
    private IMaintenanceService maintenanceService;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<MaintenanceResDTO> maintenanceResDTOS = maintenanceService.findAllMaintenanceResDTO();

        if (maintenanceResDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(maintenanceResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã dịch vụ bảo dưỡng không hợp lệ");
        }
        Long maintenanceId = Long.parseLong(id);

        Optional<Maintenance> maintenanceOptional = maintenanceService.findById(maintenanceId);

        if (maintenanceOptional.isEmpty()) {
            throw new DataInputException("Dịch vụ bảo dưỡng không tồn tại !");
        }

        return new ResponseEntity<>(maintenanceOptional.get().toMaintenanceResDTO(), HttpStatus.OK);
    }

    @GetMapping(path = "/export-to-excel")
    public ResponseEntity<StreamingResponseBody> exportMaintenanceFromDBtoExCel(HttpServletResponse response) {

        StreamingResponseBody responseBody = maintenanceService.exportToExcel(response);

        return ResponseEntity.ok(responseBody);

    }

    @PostMapping()
    private ResponseEntity<?> create(@ModelAttribute MaintenanceCreateReqDTO maintenanceCreateReqDTO, BindingResult bindingResult) {

        new MaintenanceCreateReqDTO().validate(maintenanceCreateReqDTO, bindingResult);

        Maintenance maintenance = maintenanceCreateReqDTO.toMaintenance(null);

        maintenanceService.save(maintenance);

        MaintenanceCreateResDTO maintenanceCreateResDTO = maintenance.toMaintenanceCreateResDTO();

        return new ResponseEntity<>(maintenanceCreateResDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = "/import-to-db")
    public ResponseEntity<?> importMaintenanceFromExcelToDb(@RequestPart MultipartFile file) {

        maintenanceService.importToDb(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @ModelAttribute MaintenanceUpdateReqDTO maintenanceUpdateReqDTO, BindingResult bindingResult) {

        new MaintenanceUpdateReqDTO().validate(maintenanceUpdateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã dịch vụ bảo dưỡng không hợp lệ");
        }

        Long maintenanceId = Long.parseLong(id);

        Optional<Maintenance> maintenanceOptional = maintenanceService.findByIdAndDeletedIsFalse(maintenanceId);

        if (maintenanceOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin dịch vụ bảo dưỡng!");
        }

        Maintenance maintenance = maintenanceService.update(maintenanceOptional.get(), maintenanceUpdateReqDTO);

        MaintenanceUpdateResDTO maintenanceUpdateResDTO = maintenance.toMaintenanceUpdateResDTO();

        return new ResponseEntity<>(maintenanceUpdateResDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã dịch vụ bảo dưỡng không hợp lệ");
        }
        Long maintenanceId = Long.parseLong(id);

        Optional<Maintenance> maintenanceOptional = maintenanceService.findByIdAndDeletedIsFalse(maintenanceId);

        if (maintenanceOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin dịch vụ!");
        }

        Maintenance maintenance = maintenanceOptional.get();
        maintenance.setDeleted(true);

        maintenanceService.save(maintenance);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
