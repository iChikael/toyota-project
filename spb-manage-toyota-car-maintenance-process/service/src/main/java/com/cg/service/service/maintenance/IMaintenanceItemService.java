package com.cg.service.service.maintenance;

import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemCreateReqDTO;
import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemResDTO;
import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemUpdateReqDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


public interface IMaintenanceItemService extends IGeneralService<MaintenanceItem, Long> {
    Optional<MaintenanceItem> findByIdAndDeletedIsFalse(Long id);

    List<MaintenanceItemResDTO> findAllMaintenanceItemResDTO();

    MaintenanceItem create(MaintenanceItemCreateReqDTO maintenanceItemCreateReqDTO, ServiceArea serviceArea);

    MaintenanceItem update(MaintenanceItem maintenanceItem, MaintenanceItemUpdateReqDTO maintenanceItemUpdateReqDTO, ServiceArea serviceArea);


    void importToDb(MultipartFile multipartfile);

    StreamingResponseBody exportToExcel(HttpServletResponse response);

}
