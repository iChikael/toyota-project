package com.cg.repository.car;


import com.cg.domain.dto.car.CarResDTO;
import com.cg.domain.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICarRepository extends JpaRepository<Car, Long> {

    @Query ("SELECT NEW com.cg.domain.dto.car.CarResDTO (" +
                "ca.id, " +
                "ca.code, " +
                "ca.title, " +
                "ca.price, " +
                "ca.description, " +
                "ca.seatCode, " +
                "ca.fuelCode, " +
                "ca.originCode, " +
                "ca.vehicle, " +
                "ca.carAvatar " +
            ") " +
            "FROM Car as ca " +
            "WHERE ca.deleted = false"
    )
    List<CarResDTO> findAllCarResDTO();

    @Query ("SELECT NEW com.cg.domain.dto.car.CarResDTO (" +
                "ca.id, " +
                "ca.code, " +
                "ca.title, " +
                "ca.price, " +
                "ca.description, " +
                "ca.seatCode, " +
                "ca.fuelCode, " +
                "ca.originCode, " +
                "ca.vehicle, " +
                "ca.carAvatar " +
            ") " +
            "FROM Car as ca " +
            "WHERE ca.deleted = false " +
            "AND ca.title LIKE %?1%"
    )
    List<CarResDTO> findAllCarResDTOSByKeyWord( String name);
    @Query ("SELECT NEW com.cg.domain.dto.car.CarResDTO (" +
                "ca.id, " +
                "ca.code, " +
                "ca.title, " +
                "ca.price, " +
                "ca.description, " +
                "ca.seatCode, " +
                "ca.fuelCode, " +
                "ca.originCode, " +
                "ca.vehicle, " +
                "ca.carAvatar" +
            ") " +
            "FROM Car as ca " +
            "WHERE ca.deleted = false " +
            "AND ca.id = :carId"
    )
    Optional<CarResDTO> findCarResDTOById(Long carId);


    List<Car> getAllByTitle(String title);

    Boolean existsCarByTitle(String title);


}
