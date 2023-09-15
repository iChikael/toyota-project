package com.cg.repository.service.serviceArea;

import com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO;
import com.cg.domain.entity.service.ServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IServiceAreaRepository extends JpaRepository <ServiceArea, Long> {

    Optional<ServiceArea> findByIdAndDeletedIsFalse (Long id);

    @Query("SELECT NEW com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO ( " +
                "serA.id, " +
                "serA.name, " +
                "serA.capacity, " +
                "serA.currentCapacity, " +
                "serA.status" +
            ") " +
            "FROM ServiceArea as serA " +
            "WHERE serA.deleted = false"
    )
    List<ServiceAreaResDTO> findAllServiceAreasDTO();
}
