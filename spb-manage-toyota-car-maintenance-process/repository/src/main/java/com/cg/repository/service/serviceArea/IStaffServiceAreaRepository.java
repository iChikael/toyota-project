package com.cg.repository.service.serviceArea;

import com.cg.domain.entity.service.StaffServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStaffServiceAreaRepository extends JpaRepository <StaffServiceArea, Long> {
}
