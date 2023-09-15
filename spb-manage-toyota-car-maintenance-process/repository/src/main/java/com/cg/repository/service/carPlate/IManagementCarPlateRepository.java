package com.cg.repository.service.carPlate;

import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateResDTO;
import com.cg.domain.entity.service.ManagementCarPlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IManagementCarPlateRepository extends JpaRepository <ManagementCarPlate, Long> {

    Optional<ManagementCarPlate> findManagementCarPlateByIdAndDeletedIsFalse (Long id);

    @Query("SELECT NEW com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateResDTO (" +
                "mgcp.id, " +
                "mgcp.carNumberPlate, " +
                "mgcp.car, " +
                "mgcp.customer " +
            ") " +
            "FROM ManagementCarPlate as mgcp " +
            "WHERE mgcp.carNumberPlate = :carPlate "
    )
    Optional<ManagementCarPlateResDTO> findAllManagementCarPlateResDTOByCarPlate(String carPlate);

    @Query("SELECT NEW com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateResDTO (" +
                "mgcp.id, " +
                "mgcp.carNumberPlate, " +
                "mgcp.car, " +
                "mgcp.customer " +
            ") " +
            "FROM ManagementCarPlate as mgcp " +
            "WHERE mgcp.carNumberPlate = :carPlate "
    )
    Optional<ManagementCarPlateResDTO> findManagementCarPlateByCarPlate(String carPlate);

    Boolean existsManagementCarPlateByCarNumberPlateAndCarIdAndCustomerId(String carPlate, Long carId, String customerId);

    @Query("SELECT NEW com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateResDTO (" +
                "mgcp.id, " +
                "mgcp.carNumberPlate, " +
                "mgcp.car, " +
                "mgcp.customer " +
            ") " +
            "FROM ManagementCarPlate as mgcp " +
            "WHERE mgcp.carNumberPlate LIKE %?1% "
    )
    List<ManagementCarPlateResDTO> findAllManagementCarPlateByKeyWord(String key);

    @Query("SELECT NEW com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateResDTO (" +
                "mgcp.id, " +
                "mgcp.carNumberPlate, " +
                "mgcp.car, " +
                "mgcp.customer " +
            ") " +
            "FROM ManagementCarPlate as mgcp"
    )
    List<ManagementCarPlateResDTO> findAllManagementCarPlateResDTO();
}
