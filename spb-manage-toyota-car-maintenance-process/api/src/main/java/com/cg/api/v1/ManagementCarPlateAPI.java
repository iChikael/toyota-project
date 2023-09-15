package com.cg.api.v1;

import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateCreateReqDTO;
import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateResDTO;
import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateUpdateReqDTO;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.service.ManagementCarPlate;
import com.cg.exception.DataInputException;
import com.cg.service.customer.ICustomerService;
import com.cg.service.service.carPlate.IManagementCarPlateService;
import com.cg.service.user.IUserService;
import com.cg.utils.ValidateUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/car-plates")
@ConfigurationProperties(prefix = "application.ocr")
public class ManagementCarPlateAPI {

    @Autowired
    private IManagementCarPlateService managementCarPlateService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ValidateUtils validateUtils;

    @Value("${application.ocr.server-url}")
    private String ocrServer;

    @GetMapping
    public ResponseEntity<List<?>> getAllCarPlate() {
        List<ManagementCarPlateResDTO> managementCarPlateResDTOList = managementCarPlateService.findAllManagementCarPlateResDTO();

        if (managementCarPlateResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(managementCarPlateResDTOList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCarPlateById(@PathVariable String id) {
        try {
            Optional<ManagementCarPlate> managementCarPlateOptional = managementCarPlateService.findManagementCarPlateByIdAndDeletedIsFalse(Long.parseLong(id));

            if (managementCarPlateOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(managementCarPlateOptional.get().toManagementCarPlateCreateResDTO(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{carPlate}")
    public ResponseEntity<?> getAllCarPlateByCarPlate(@PathVariable String carPlate) {
        Optional<ManagementCarPlateResDTO> managementCarPlateResDTOOptional = managementCarPlateService.findAllManagementCarPlateResDTOByCarPlate(carPlate);

        if (managementCarPlateResDTOOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(managementCarPlateResDTOOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<?>> search(@RequestParam(name = "key", required = false) String key) {

        if (key.length() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ManagementCarPlateResDTO> managementCarPlateResDTOList = managementCarPlateService.findAllManagementCarPlateByKeyWord(key);

        if (managementCarPlateResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(managementCarPlateResDTOList, HttpStatus.OK);
    }

    @PostMapping("/search-by-image-base64")
    public ResponseEntity<?> searchByImageBase64(HttpServletRequest keyword) throws IOException {

        String body = keyword.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        ObjectMapper mapper = new JsonMapper();
        JsonNode json = mapper.readTree(body);

        String base64 = json.get("base64").asText();

        Map<String, String> requestEntity = new HashMap<>();
        requestEntity.put("result", base64);

        String uri = ocrServer + "/ocr/upload-base64";
        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.postForObject(uri, requestEntity, String.class);

        Optional<ManagementCarPlateResDTO> managementCarPlateResDTOOptional = managementCarPlateService.findManagementCarPlateByCarPlate(response);

        if (managementCarPlateResDTOOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(managementCarPlateResDTOOptional.get());

    }

    @PostMapping("/create")
    public ResponseEntity<?> createCarPlate(@RequestBody ManagementCarPlateCreateReqDTO managementCarPlateCreateReqDTO, BindingResult bindingResult) {

        new ManagementCarPlateCreateReqDTO().validate(managementCarPlateCreateReqDTO, bindingResult);


        Optional<Customer> customerOptional = customerService.findByUser(userService.getByUsername(managementCarPlateCreateReqDTO.getUsername()));

        if (customerOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin khách hàng!");
        }

        if (managementCarPlateService.existsManagementCarPlateByCarNumberPlateAndCarIdAndCustomerId(managementCarPlateCreateReqDTO.getCarNumberPlate(), Long.parseLong(managementCarPlateCreateReqDTO.getCarId()), customerOptional.get().getId())) {
            throw new DataInputException("Thông tin đã tồn tại");
        }

        ManagementCarPlate managementCarPlate = managementCarPlateService.create(managementCarPlateCreateReqDTO, customerOptional.get());

        return new ResponseEntity<>(managementCarPlate.toManagementCarPlateCreateResDTO(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCarPlate(@PathVariable String id, @RequestBody ManagementCarPlateUpdateReqDTO managementCarPlateUpdateReqDTO, BindingResult bindingResult) {

        new ManagementCarPlateUpdateReqDTO().validate(managementCarPlateUpdateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã quản lý biển số xe không hợp lệ");
        }

        Long managementCarPlateId = Long.parseLong(id);

        Optional<ManagementCarPlate> managementCarPlateOptional = managementCarPlateService.findById(managementCarPlateId);

        if (managementCarPlateOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin quản lý biển số xe!");
        }

        Optional<Customer> customerOptional = customerService.findByUser(userService.getByUsername(managementCarPlateUpdateReqDTO.getUsername()));

        if (customerOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin khách hàng!");
        }

        if (managementCarPlateService.existsManagementCarPlateByCarNumberPlateAndCarIdAndCustomerId(managementCarPlateOptional.get().getCarNumberPlate(), managementCarPlateOptional.get().getCar().getId(), customerOptional.get().getId())) {
            throw new DataInputException("Thông tin đã tồn tại");
        }

        ManagementCarPlate managementCarPlate = managementCarPlateService.update(managementCarPlateOptional.get(), customerOptional.get(), managementCarPlateUpdateReqDTO);

        return new ResponseEntity<>(managementCarPlate.toManagementCarPlateUpdateResDTO(), HttpStatus.OK);
    }
}
