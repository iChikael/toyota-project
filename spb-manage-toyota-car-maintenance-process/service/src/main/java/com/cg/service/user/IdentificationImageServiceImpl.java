package com.cg.service.user;

import com.cg.cloudinary.CloudinaryUploadUtil;
import com.cg.domain.entity.IdentificationImage;
import com.cg.domain.entity.user.User;
import com.cg.exception.DataInputException;
import com.cg.repository.user.IIdentificationImageRepository;
import com.cg.service.upload.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class IdentificationImageServiceImpl implements IIdentificationImageService{

    @Autowired
    private IIdentificationImageRepository iIdentificationImageRepository;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private CloudinaryUploadUtil cloudinaryUploadUtil;


    @Override
    public List<IdentificationImage> findAll() {
        return iIdentificationImageRepository.findAll();
    }

    @Override
    public Optional<IdentificationImage> findById(String id) {
        return iIdentificationImageRepository.findById(id);
    }

    @Override
    public IdentificationImage save(IdentificationImage identificationImage) {
        return iIdentificationImageRepository.save(identificationImage);
    }

    @Override
    public void delete(IdentificationImage identificationImage) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public IdentificationImage createImage(MultipartFile identificationImage, User user) {
        IdentificationImage identificationImageNew = new IdentificationImage();
        identificationImageNew.setUser(user);
        iIdentificationImageRepository.save(identificationImageNew);

        uploadAndSaveIdentificationImage(identificationImage, identificationImageNew);

        return identificationImageNew;
    }

    private void uploadAndSaveIdentificationImage(MultipartFile avatar, IdentificationImage identificationImage) {

        try {
            Map uploadResult = uploadService.uploadImage(avatar, cloudinaryUploadUtil.buildImageUploadParams(identificationImage.getId(), cloudinaryUploadUtil.IDENTIFICATION_IMAGE_UPLOAD_FOLDER, cloudinaryUploadUtil.ERROR_IMAGE_UPLOAD));

            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            identificationImage.setFileName(identificationImage.getId() + "." + fileFormat);
            identificationImage.setFileUrl(fileUrl);
            identificationImage.setFileFolder(cloudinaryUploadUtil.STAFF_IMAGE_UPLOAD_FOLDER);
            identificationImage.setCloudId(identificationImage.getFileFolder() + "/" + identificationImage.getId());
            iIdentificationImageRepository.save(identificationImage);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }
}
