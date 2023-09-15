package com.cg.repository.staff;

import com.cg.domain.dto.DashboardDTO;
import com.cg.domain.dto.staff.StaffResDTO;
import com.cg.domain.entity.staff.Staff;
import com.cg.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, String> {

    @Query("SELECT NEW com.cg.domain.dto.staff.StaffResDTO ( " +
                "sf.id, " +
                "sf.fullName, " +
                "sf.identificationNumber, " +
                "sf.email, " +
                "sf.dob, " +
                "sf.phone, " +
                "sf.staffLocation, " +
                "sf.user " +
            ") " +
            "FROM Staff AS sf " +
            "WHERE sf.deleted = false " +
            "ORDER BY sf.createdAt DESC "
    )
    List<StaffResDTO> findAllStaffResDTO();

    @Query("SELECT NEW com.cg.domain.dto.staff.StaffResDTO ( " +
                "sf.id, " +
                "sf.fullName, " +
                "sf.identificationNumber, " +
                "sf.email, " +
                "sf.dob, " +
                "sf.phone, " +
                "sf.staffLocation, " +
                "sf.user " +
            ") " +
            "FROM Staff AS sf " +
            "WHERE sf.deleted = false " +
            "AND sf.id = :id "
    )
    Optional<StaffResDTO> findStaffByIdResDTO(@Param("id") String id);

    Optional<Staff> findByUser(User user);

    @Query(value = "SELECT * FROM vw_info_dashboard", nativeQuery = true)
    DashboardDTO getDashboardInfo();
}
