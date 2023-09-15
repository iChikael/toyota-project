package com.cg.api.v1;

import com.cg.domain.dto.car.*;
import com.cg.domain.entity.car.Car;
import com.cg.domain.entity.car.Vehicle;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.service.car.ICarService;
import com.cg.service.car.IVehicleService;
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
@RequestMapping("/api/v1/cars")
public class CarAPI {

    @Autowired
    private ICarService carService;

    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<List<?>> getAllCarDTO() {

        List<CarResDTO> carResDTOS = carService.findAllCarResDTO();

        if (carResDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(carResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarDTOById(@PathVariable String id) {

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã xe không hợp lệ");
        }
        Long carId = Long.parseLong(id);

        Optional<CarResDTO> carResDTOOptional = carService.findCarResDTOById(carId);

        if (carResDTOOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CarResDTO carResDTO = carResDTOOptional.get();

        return new ResponseEntity<>(carResDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<?>> search(@RequestParam(name = "name", required = false) String name) {
        if (name.length() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CarResDTO> carResDTOS = carService.findAllCarResDTOSByKeyWord(name);

        if (carResDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(carResDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "/export-to-excel")
    public ResponseEntity<StreamingResponseBody> downloadTransactions(HttpServletResponse response) {

        StreamingResponseBody responseBody = carService.exportToExcel(response);

        return ResponseEntity.ok(responseBody);

    }

    @PostMapping("/create")
    public ResponseEntity<?> createCar(@ModelAttribute CarCreateReqDTO carCreateReqDTO, BindingResult bindingResult) {

        new CarCreateReqDTO().validate(carCreateReqDTO, bindingResult);

        Vehicle vehicle = vehicleService.findById(carCreateReqDTO.getVehicleId()).orElseThrow(() -> {
            throw new DataInputException("Dòng xe không tồn tại");
        });

        Boolean isExistCar = carService.isExistCar(carCreateReqDTO.getTitle());

        if (isExistCar) {
            throw new EmailExistsException("Xe đã tồn tại");
        }

        Car car = carService.createCar(carCreateReqDTO, vehicle);

        CarCreateResDTO carCreateResDTO = car.toCarCreateResDTO();

        return new ResponseEntity<>(carCreateResDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/import-to-db")
    public ResponseEntity<?> importTransactionsFromExcelToDb(@RequestPart MultipartFile file) {

        carService.importToDb(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable String id, @ModelAttribute CarUpdateReqDTO carUpdateReqDTO, BindingResult bindingResult) {

        new CarUpdateReqDTO().validate(carUpdateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã xe không hợp lệ");
        }

        Long carId = Long.parseLong(id);

        Optional<Car> carOptional = carService.findById(carId);

        Vehicle vehicle = vehicleService.findById(carUpdateReqDTO.getVehicleId()).orElseThrow(() -> {
            throw new DataInputException("Dòng xe không tồn tại");
        });

        if (carOptional.isPresent()) {

            Car car = carService.updateCar(carOptional.get(), carUpdateReqDTO, vehicle);

            CarUpdateResDTO carUpdateResDTO = car.toCarUpdateResDTO();

            return new ResponseEntity<>(carUpdateResDTO, HttpStatus.OK);

        } else {
            throw new DataInputException("Không tìm thấy thông tin xe!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable String id) {

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã xe không hợp lệ");
        }

        Long carId = Long.parseLong(id);

        Optional<Car> carOptional = carService.findById(carId);

        if (carOptional.isPresent()) {

            Car car = carOptional.get();

            car.setDeleted(true);

            carService.save(car);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new DataInputException("Không tìm thấy thông tin xe!");
        }
    }
}
