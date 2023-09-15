package com.cg.repository.car;

import com.cg.domain.dto.car.VehicleResDTO;
import com.cg.domain.entity.car.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle,Long> {

    @Query("SELECT NEW com.cg.domain.dto.car.VehicleResDTO (" +
                "vehi.id, " +
                "vehi.name, " +
                "vehi.modelCode" +
            ") " +
            "FROM Vehicle AS vehi"
    )
    List<VehicleResDTO> findAllVehicleResDTO();
}
