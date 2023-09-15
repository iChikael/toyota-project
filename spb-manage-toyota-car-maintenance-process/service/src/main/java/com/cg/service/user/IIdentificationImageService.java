package com.cg.service.user;

import com.cg.domain.entity.IdentificationImage;
import com.cg.domain.entity.user.User;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

public interface IIdentificationImageService extends IGeneralService <IdentificationImage, String> {
    IdentificationImage createImage(MultipartFile identificationImage, User user);
}
