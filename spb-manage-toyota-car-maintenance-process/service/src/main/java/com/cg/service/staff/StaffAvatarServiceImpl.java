package com.cg.service.staff;

import com.cg.cloudinary.CloudinaryUploadUtil;
import com.cg.domain.entity.staff.Staff;
import com.cg.domain.entity.staff.StaffAvatar;
import com.cg.exception.DataInputException;
import com.cg.repository.staff.IStaffAvatarRepository;
import com.cg.service.upload.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class StaffAvatarServiceImpl implements IStaffAvatarService {

    @Autowired
    private IStaffAvatarRepository staffAvatarRepository;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private CloudinaryUploadUtil cloudinaryUploadUtil;


    @Override
    public List<StaffAvatar> findAll() {
        return staffAvatarRepository.findAll();
    }

    @Override
    public Optional<StaffAvatar> findById(String id) {
        return staffAvatarRepository.findById(id);
    }

    @Override
    public StaffAvatar save(StaffAvatar staffAvatar) {
        return staffAvatarRepository.save(staffAvatar);
    }

    @Override
    public void delete(StaffAvatar staffAvatar) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Optional<StaffAvatar> findStaffAvatarByStaffIdAndDeletedIsFalse(String id) {
        return staffAvatarRepository.findStaffAvatarByStaffIdAndDeletedIsFalse(id);
    }

    @Override
    public StaffAvatar createStaffAvatar(MultipartFile avatar, Staff staff) {

        StaffAvatar staffAvatar = new StaffAvatar();
        staffAvatar.setStaff(staff);
        staffAvatarRepository.save(staffAvatar);
        uploadAndSaveStaffAvatar(avatar, staffAvatar);
        return staffAvatar;
    }

    private void uploadAndSaveStaffAvatar(MultipartFile avatar, StaffAvatar staffAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(avatar, cloudinaryUploadUtil.buildImageUploadParams(staffAvatar.getId(), cloudinaryUploadUtil.STAFF_IMAGE_UPLOAD_FOLDER, cloudinaryUploadUtil.ERROR_IMAGE_UPLOAD));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            staffAvatar.setFileName(staffAvatar.getId() + "." + fileFormat);
            staffAvatar.setFileUrl(fileUrl);
            staffAvatar.setFileFolder(cloudinaryUploadUtil.STAFF_IMAGE_UPLOAD_FOLDER);
            staffAvatar.setCloudId(staffAvatar.getFileFolder() + "/" + staffAvatar.getId());
            staffAvatarRepository.save(staffAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }
}
