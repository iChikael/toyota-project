package com.cg.service.bill;

import com.cg.domain.dto.service.billService.BillServiceDetailAccessoryResDTO;
import com.cg.domain.entity.bill.BillServiceDetailAccessory;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IBillServiceDetailAccessoryService extends IGeneralService<BillServiceDetailAccessory, Long> {

    List<BillServiceDetailAccessoryResDTO> findBillServiceDetailAccessoryResDTOByBillServiceDetailId(Long billServiceDetailId);
}
