package com.cg.api.v1;

import com.cg.domain.dto.DashboardDTO;
import com.cg.service.staff.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboards")
public class DashboardAPI {

    @Autowired
    private IStaffService staffService;

    @GetMapping
    public ResponseEntity<?> getAllInfoDashboard() {
        DashboardDTO dashboardDTO = staffService.getDashboardInfo();
        return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
    }

}
