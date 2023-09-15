package com.cg.api.v1;

import com.cg.domain.dto.service.repairItem.*;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.service.IServiceAreaService;
import com.cg.service.service.repair.IRepairItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/repair-items")
public class RepairItemAPI {

    @Autowired
    private IServiceAreaService serviceAreaService;

    @Autowired
    private IRepairItemService repairItemService;

    @GetMapping
    public ResponseEntity<?> getAll() {

        List<RepairItemResDTO> repairItemResDTOS = repairItemService.findAllRepairItemsDTO();

        return new ResponseEntity<>(repairItemResDTOS, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Optional<RepairItem> repairItemOptional = repairItemService.findById(id);

        if (!repairItemOptional.isPresent()) {
            throw new ResourceNotFoundException("Dịch vụ không tồn tại !");
        }

        RepairItem repairItem = repairItemOptional.get();

        RepairItemResDTO repairItemResDTO = repairItem.toRepairItemResDTO();

        return new ResponseEntity<>(repairItemResDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/export-to-excel")
    public ResponseEntity<StreamingResponseBody> exportRepairItemsFromDBtoExCel(HttpServletResponse response) {

        StreamingResponseBody responseBody = repairItemService.exportToExcel(response);

        return ResponseEntity.ok(responseBody);

    }

    @PostMapping(path = "/import-to-db")
    public ResponseEntity<?> importRepairItemsFromExcelToDb(@RequestPart MultipartFile file) {

        repairItemService.importToDb(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RepairItemCreateReqDTO repairItemCreateReqDTO) {
        ServiceArea serviceArea = serviceAreaService.findById(Long.valueOf(repairItemCreateReqDTO.getServiceArea())).orElseThrow(() -> {
            throw new DataInputException("Khu vực không tồn tại");
        });

        RepairItem repairItem = repairItemService.createRepairItem(repairItemCreateReqDTO, serviceArea);

        RepairItemCreateResDTO repairItemCreateResDTO = repairItem.toRepairItemCreateResDTO();

        return new ResponseEntity<>(repairItemCreateResDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RepairItemUpdateReqDTO repairItemUpdateReqDTO) {

        Optional<RepairItem> repairItemOptional = repairItemService.findById(id);

        ServiceArea serviceArea = serviceAreaService.findById(Long.valueOf(repairItemUpdateReqDTO.getServiceArea())).orElseThrow(() -> {
            throw new DataInputException("Khu vực không tồn tại");
        });

        if (repairItemOptional.isPresent()) {

            RepairItem repairItem = repairItemService.updateRepairItem(repairItemOptional.get(), repairItemUpdateReqDTO, serviceArea);

            RepairItemUpdateResDTO repairItemUpdateResDTO = repairItem.toRepairItemUpdateResDTO();

            return new ResponseEntity<>(repairItemUpdateResDTO, HttpStatus.OK);

        } else {
            throw new DataInputException("Không tìm thấy thông tin dịch vụ!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<RepairItem> repairItemOptional = repairItemService.findById(id);
        if (repairItemOptional.isPresent()) {

            RepairItem repairItem = repairItemOptional.get();

            repairItem.setDeleted(true);

            repairItemService.save(repairItem);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        } else {
            throw new DataInputException("Không tìm thấy thông tin dịch vụ!");
        }
    }
}
