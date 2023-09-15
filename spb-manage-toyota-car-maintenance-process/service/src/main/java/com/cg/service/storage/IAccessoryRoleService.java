package com.cg.service.storage;

import com.cg.domain.dto.accessory.AccessoryRoleResDTO;
import com.cg.domain.entity.storage.AccessoryRole;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IAccessoryRoleService extends IGeneralService<AccessoryRole, Long> {
    List<AccessoryRoleResDTO> getAllAccessoryResDTO();
}
