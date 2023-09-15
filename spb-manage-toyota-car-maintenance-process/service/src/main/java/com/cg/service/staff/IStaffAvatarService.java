package com.cg.service.staff;

import com.cg.domain.entity.staff.Staff;
import com.cg.domain.entity.staff.StaffAvatar;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public interface IStaffAvatarService extends IGeneralService<StaffAvatar, String> {
    Optional<StaffAvatar> findStaffAvatarByStaffIdAndDeletedIsFalse(String id);

    StaffAvatar createStaffAvatar(MultipartFile avatar, Staff staff);
}
