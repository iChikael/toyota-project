package com.cg.service.service.repair;

import com.cg.domain.dto.service.repairItem.RepairItemCreateReqDTO;
import com.cg.domain.dto.service.repairItem.RepairItemResDTO;
import com.cg.domain.dto.service.repairItem.RepairItemUpdateReqDTO;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface IRepairItemService extends IGeneralService<RepairItem, Long> {

    RepairItem createRepairItem(RepairItemCreateReqDTO repairCreateReqDTO, ServiceArea serviceArea);

    RepairItem updateRepairItem(RepairItem repairItem, RepairItemUpdateReqDTO repairItemUpdateReqDTO, ServiceArea serviceArea);


    void importToDb(MultipartFile multipartfile);

    StreamingResponseBody exportToExcel(HttpServletResponse response);

    Optional<RepairItem> findByIdAndDeletedIsFalse(Long id);

    List<RepairItemResDTO> findAllRepairItemsDTO();

}
