package com.cg.service.car;

import com.cg.domain.dto.car.CarCreateReqDTO;
import com.cg.domain.dto.car.CarResDTO;
import com.cg.domain.dto.car.CarUpdateReqDTO;
import com.cg.domain.entity.car.Car;
import com.cg.domain.entity.car.Vehicle;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface ICarService extends IGeneralService <Car, Long> {

    List<CarResDTO> findAllCarResDTO();
    List<CarResDTO> findAllCarResDTOSByKeyWord( String name);

    Optional<CarResDTO> findCarResDTOById(Long carId);

    Car createCar(CarCreateReqDTO carCreateReqDTO, Vehicle vehicle);

    Car updateCar(Car car, CarUpdateReqDTO carUpdateReqDTO, Vehicle vehicle);

    void importToDb(MultipartFile multipartfile);

    StreamingResponseBody exportToExcel(HttpServletResponse response);

    Boolean isExistCar (String title);
}
