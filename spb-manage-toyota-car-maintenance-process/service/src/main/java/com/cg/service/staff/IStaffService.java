package com.cg.service.staff;

import com.cg.domain.dto.DashboardDTO;
import com.cg.domain.dto.staff.StaffResDTO;
import com.cg.domain.dto.staff.StaffUpdateInfoReqDTO;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.staff.Staff;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface IStaffService extends IGeneralService<Staff, String> {

    Optional<Staff> findByUser(User user);

    List<StaffResDTO> findAllStaffResDTO();

    Staff updateStaff(Staff staff, StaffUpdateInfoReqDTO staffUpdateInfoReqDTO);

    Optional<StaffResDTO> findStaffByIdResDTO(String username);

    void importToDb(MultipartFile multipartfile);

    StreamingResponseBody exportToExcel(HttpServletResponse response);

    DashboardDTO getDashboardInfo();
}
