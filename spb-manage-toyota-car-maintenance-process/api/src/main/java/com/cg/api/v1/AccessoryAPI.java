package com.cg.api.v1;

import com.cg.domain.dto.accessory.*;
import com.cg.domain.entity.storage.Accessory;
import com.cg.domain.entity.storage.AccessoryRole;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.service.storage.IAccessoryRoleService;
import com.cg.service.storage.IAccessoryService;
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
@RequestMapping("/api/v1/storage")
public class AccessoryAPI {

    @Autowired
    private IAccessoryService accessoryService;

    @Autowired
    private IAccessoryRoleService accessoryRoleService;

    @GetMapping
    public ResponseEntity<?> getAll() {

        List<AccessoryResDTO> accessories = accessoryService.getAllAccessoryResDTO();

        return new ResponseEntity<>(accessories, HttpStatus.OK);
    }

    @GetMapping("/accessory-role")
    public ResponseEntity<?> getAllAccessoryRole() {

        List<AccessoryRoleResDTO> accessoryRoles = accessoryRoleService.getAllAccessoryResDTO();

        return new ResponseEntity<>(accessoryRoles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        Optional<Accessory> accessoryOptional = accessoryService.findById(id);

        if (accessoryOptional.isEmpty()) {
            throw new DataInputException("Linh kiện không tồn tại !");
        }

        Accessory accessory = accessoryOptional.get();
        AccessoryResDTO accessoryResDTO = accessory.toAccessoryResDTO();

        return new ResponseEntity<>(accessoryResDTO, HttpStatus.OK);
    }

    @GetMapping("/reach/{code}")
    public ResponseEntity<AccessoryResDTO> getByCode(@PathVariable String code) {
        Optional<Accessory> accessoryOptional = accessoryService.getByCode(code);

        if (accessoryOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Accessory accessory = accessoryOptional.get();
        AccessoryResDTO accessoryResDTO = accessory.toAccessoryResDTO();
        return new ResponseEntity<>(accessoryResDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/export-to-excel")
    public ResponseEntity<StreamingResponseBody> exportAccessoriesFromDBtoExCel(HttpServletResponse response) {

        StreamingResponseBody responseBody = accessoryService.exportToExcel(response);

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AccessoryCreateReqDTO accessoryCreateReqDTO) {

        AccessoryRole accessoryRole = accessoryRoleService.findById(accessoryCreateReqDTO.getAccessoryRole())
                .orElseThrow(() -> {
                    throw new DataInputException("Loại linh kiện không tồn tại");
                });

        Boolean isExistAccessory = accessoryService.existsByCode(accessoryCreateReqDTO.getCode());

        if (isExistAccessory) {
            throw new EmailExistsException("Linh kiện đã tồn tại");
        }

        Accessory accessory = accessoryService.createAccessory(accessoryCreateReqDTO, accessoryRole);
        AccessoryCreateResDTO accessoryCreateResDTO = accessory.toAccessoryCreateResDTO();

        return new ResponseEntity<>(accessoryCreateResDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = "/import-to-db")
    public ResponseEntity<?> importAccessoriesFromExcelToDb(@RequestPart MultipartFile file) {

        accessoryService.importToDb(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AccessoryUpdateReqDTO accessoryUpdateReqDTO) {

        Optional<Accessory> accessoryOptional = accessoryService.findById(id);

        if (accessoryOptional.isEmpty()) {
            throw new DataInputException("Linh kiện không tồn tại !");
        }

        AccessoryRole accessoryRole = accessoryRoleService.findById(accessoryUpdateReqDTO.getAccessoryRole()).orElseThrow(() -> new DataInputException("Loại linh kiện không tồn tại !"));

        Accessory accessory = accessoryUpdateReqDTO.toAccessory(accessoryRole);

        accessoryService.updateAccessory(accessoryOptional.get(), accessoryRole, accessoryUpdateReqDTO);
        AccessoryUpdateResDTO accessoryUpdateResDTO = accessory.toAccessoryUpdateResDTO();

        return new ResponseEntity<>(accessoryUpdateResDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Accessory> accessoryOptional = accessoryService.findById(id);

        if (accessoryOptional.isEmpty()) {
            throw new DataInputException("Linh kiện không tồn tại !");
        }

        Accessory accessory = accessoryOptional.get();
        accessory.setDeleted(true);

        accessoryService.save(accessory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
