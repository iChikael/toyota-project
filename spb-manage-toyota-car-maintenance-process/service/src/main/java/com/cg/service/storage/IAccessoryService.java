package com.cg.service.storage;


import com.cg.domain.dto.accessory.AccessoryCreateReqDTO;
import com.cg.domain.dto.accessory.AccessoryReceptionResDTO;
import com.cg.domain.dto.accessory.AccessoryResDTO;
import com.cg.domain.dto.accessory.AccessoryUpdateReqDTO;
import com.cg.domain.entity.storage.Accessory;
import com.cg.domain.entity.storage.AccessoryRole;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface IAccessoryService extends IGeneralService<Accessory, Long> {

    List<AccessoryResDTO> getAllAccessoryResDTO();
    List<AccessoryReceptionResDTO> findAllAccessoryMaintenanceItems();

    Optional<Accessory> findByIdAndDeletedIsFalse (Long id);
    Accessory createAccessory(AccessoryCreateReqDTO accessoryCreateReqDTO, AccessoryRole accessoryRole);

    Accessory updateAccessory(Accessory accessory,AccessoryRole accessoryRole, AccessoryUpdateReqDTO accessoryUpdateReqDTO);

    void importToDb(MultipartFile multipartfile);

    StreamingResponseBody exportToExcel(HttpServletResponse response);

    Optional<Accessory> getByCode(String code);

    Boolean existsByCode(String code);



}

