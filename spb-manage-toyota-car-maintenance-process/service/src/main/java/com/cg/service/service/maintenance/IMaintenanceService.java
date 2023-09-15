package com.cg.service.service.maintenance;

import com.cg.domain.dto.service.maintaince.MaintenanceResDTO;
import com.cg.domain.dto.service.maintaince.MaintenanceUpdateReqDTO;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface IMaintenanceService extends IGeneralService<Maintenance, Long> {
    List<MaintenanceResDTO> findAllMaintenanceResDTO();

    Optional<Maintenance> findByIdAndDeletedIsFalse(Long id);

    Maintenance update(Maintenance maintenance, MaintenanceUpdateReqDTO maintenanceUpdateReqDTO);

    void importToDb(MultipartFile multipartfile);

    StreamingResponseBody exportToExcel(HttpServletResponse response);
}


