package com.cg.api.v1;

import com.cg.domain.dto.service.billService.BillServiceDetailAccessoryResDTO;
import com.cg.service.bill.IBillServiceDetailAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bill-service-detail-accessories")
public class BillServiceDetailAccessoryAPI {

    @Autowired
    private IBillServiceDetailAccessoryService billServiceDetailAccessoryService;

    @GetMapping("/{billServiceDetailId}")
    public ResponseEntity<?> getBillServiceDetailAccessoryByBillServiceDetailId(@PathVariable String billServiceDetailId) {
        try {
            List<BillServiceDetailAccessoryResDTO> billServiceDetailAccessoryResDTOList = billServiceDetailAccessoryService.findBillServiceDetailAccessoryResDTOByBillServiceDetailId(Long.parseLong(billServiceDetailId));
            if (billServiceDetailAccessoryResDTOList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(billServiceDetailAccessoryResDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
