package com.cg.repository.service.carPlate;

import com.cg.domain.dto.service.carqueue.CarQueueResDTO;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.enums.EStatusCarQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICarQueueRepository extends JpaRepository <CarQueue, Long> {

    @Query("SELECT NEW com.cg.domain.dto.service.carqueue.CarQueueResDTO( " +
                "cq.id, " +
                "cq.carNumberPlates, " +
                "cq.fullName, " +
                "cq.phone, " +
                "cq.status " +
            ") " +
            "FROM CarQueue AS cq " +
            "WHERE cq.deleted = false"
    )
    List<CarQueueResDTO> findAllCarQueueResDTO();

    List<CarQueue> findAllByDeletedIsFalse();

    Optional<CarQueue> findCarQueueByIdAndDeletedIsFalse (Long id);

    Optional<CarQueue> findCarQueueByCarNumberPlatesAndDeletedIsFalse (String carPlate);

    Boolean existsCarQueueByCarNumberPlatesAndStatus (String carPlate, EStatusCarQueue status);

    Boolean existsCarQueueByCarNumberPlates (String carPlate);

    Boolean existsCarQueueByCarNumberPlatesAndDeletedIsFalse(String carNumberPlates);
}
