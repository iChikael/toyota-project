package com.cg.repository.staff;

import com.cg.domain.entity.staff.StaffLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStaffLocationRepository extends JpaRepository<StaffLocation, Long> {
}
