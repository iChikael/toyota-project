package com.cg.repository.service.carPlate;

import com.cg.domain.entity.service.ManagementCarPlateCarQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IManagementCarPlateCarQueueRepository extends JpaRepository <ManagementCarPlateCarQueue, Long> {
}
