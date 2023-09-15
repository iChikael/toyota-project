package com.cg.api.v1;

import com.cg.domain.dto.service.billService.BillServiceDetailResDTO;
import com.cg.service.bill.IBillServiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bill-service-details")
public class BillServiceDetailAPI {

    @Autowired
    private IBillServiceDetailService billServiceDetailService;

    @GetMapping("/{billServiceId}")
    public ResponseEntity<?> getAllBillServiceDetailByBillServiceId(@PathVariable String billServiceId) {
        try {
            List<BillServiceDetailResDTO> billServiceDetailResDTOList = billServiceDetailService.findAllBillServiceDetailResDTOByBillServiceId(Long.parseLong(billServiceId));
            if (billServiceDetailResDTOList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(billServiceDetailResDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
