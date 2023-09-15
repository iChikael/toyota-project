package com.cg.domain.dto.service.carqueue;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarQueueCreateResDTO {
    private Long id;
    private String carNumberPlates;
    private String fullName;
    private String phone;
    private String status;

}
