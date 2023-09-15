package com.cg.domain.dto.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarAvatarReqDTO {
    private String id;
    private String fileName;
    private String fileFolder;
    private String fileUrl;
}
