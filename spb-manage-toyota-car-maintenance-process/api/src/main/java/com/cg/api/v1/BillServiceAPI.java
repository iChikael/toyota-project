package com.cg.api.v1;

import com.cg.domain.dto.service.billService.*;
import com.cg.service.bill.IBillServiceService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bill-services")
public class BillServiceAPI {

    @Autowired
    private IBillServiceService billServiceService;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<List<?>> getAllBills() {
        List<BillServiceResDTO> billServiceResDTOList = billServiceService.fillAllBillServiceResDTO();

        if (billServiceResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(billServiceResDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBillServiceById(@PathVariable String id) {
        Optional<BillServiceResDTO> optionalBillServiceResDTO = billServiceService.findBillServiceResDTOById(Long.parseLong(id));

        if (optionalBillServiceResDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(optionalBillServiceResDTO, HttpStatus.OK);
    }

    @GetMapping("/top-five")
    public ResponseEntity<List<?>> getAllBillTopFives() {
        List<IBillServiceResDTO> billServiceResDTOList = billServiceService.fillAllBillServiceTopFiveResDTO();

        if (billServiceResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(billServiceResDTOList, HttpStatus.OK);
    }

    @PostMapping("/revenue-by-day")
    public ResponseEntity<?> getRevenueByDay(@RequestParam String date) {
        if (!validateUtils.isNumberValid(date.substring(0, 4)) && !validateUtils.isNumberValid(date.substring(5, 7)) && !validateUtils.isNumberValid(date.substring(8, 10))) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        RevenueByDayDTO revenueByDay = billServiceService.findRevenueByDay(date);
        return new ResponseEntity<>(revenueByDay, HttpStatus.OK);
    }

    @PostMapping("/revenue-by-month")
    public ResponseEntity<?> getRevenueByMonth(@RequestParam String month) {
        if (!validateUtils.isNumberValid(month.substring(0, 4)) && !validateUtils.isNumberValid(month.substring(5, 7))) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        RevenueByMonthDTO revenueByMonthDTO = billServiceService.findRevenueByMonth(month);
        return new ResponseEntity<>(revenueByMonthDTO, HttpStatus.OK);

    }

    @PostMapping("/revenue-by-year")
    public ResponseEntity<?> getRevenueByYear(@RequestParam String year) {
        if (!validateUtils.isNumberValid(year)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        RevenueByYearDTO revenueByYearDTO = billServiceService.findRevenueByYear(Integer.parseInt(year));
        return new ResponseEntity<>(revenueByYearDTO, HttpStatus.OK);

    }

    @PostMapping("/revenue-by-period")
    public ResponseEntity<?> getRevenueByPeriod(@RequestParam String dateStart, @RequestParam String dateEnd) {
        if (!validateUtils.isNumberValid(dateStart.substring(0, 4)) && !validateUtils.isNumberValid(dateStart.substring(5, 7)) && !validateUtils.isNumberValid(dateStart.substring(8, 10))) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!validateUtils.isNumberValid(dateEnd.substring(0, 4)) && !validateUtils.isNumberValid(dateEnd.substring(5, 7)) && !validateUtils.isNumberValid(dateEnd.substring(8, 10))) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        RevenueByPeriodDTO revenueByPeriod = billServiceService.findRevenueByPeriod(dateStart, dateEnd);
        return new ResponseEntity<>(revenueByPeriod, HttpStatus.OK);
    }

}
