package com.cg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public interface DashboardDTO {
     Long getQuantityCustomer();
     Long getQuantityCar();
     Long getQuantityStaff();
     Long getQuantityBillService();
}
