package com.cg.service.bill;

import com.cg.domain.dto.service.billService.BillServiceDetailResDTO;
import com.cg.domain.entity.bill.BillServiceDetail;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IBillServiceDetailService extends IGeneralService<BillServiceDetail, Long> {
    List<BillServiceDetailResDTO> findAllBillServiceDetailResDTOByBillServiceId(Long billServiceId);
}
