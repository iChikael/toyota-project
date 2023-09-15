package com.cg.service.service.maintenance;

import com.cg.domain.dto.service.maintenanceItemAccessory.MaintenanceItemAccessoryResDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceItemAccessory;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IMaintenanceItemAccessoryService extends IGeneralService <MaintenanceItemAccessory, Long> {

    List<MaintenanceItemAccessoryResDTO> findAllByMaintenanceItem_Id (Long maintenanceItemId);

    void importToDb(MultipartFile multipartfile);



}
