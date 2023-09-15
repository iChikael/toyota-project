package com.cg.domain.dto.customer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerAvatarResDTO {
    private String id;
    private String fileName;
    private String fileFolder;
    private String fileUrl;
}
