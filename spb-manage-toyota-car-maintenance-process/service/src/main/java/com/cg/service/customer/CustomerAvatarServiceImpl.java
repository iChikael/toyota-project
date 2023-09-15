package com.cg.service.customer;

import com.cg.cloudinary.CloudinaryUploadUtil;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.customer.CustomerAvatar;
import com.cg.exception.DataInputException;
import com.cg.repository.customer.ICustomerAvatarRepository;
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
public class CustomerAvatarServiceImpl implements ICustomerAvatarService {

    @Autowired
    private ICustomerAvatarRepository customerAvatarRepository;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private CloudinaryUploadUtil cloudinaryUploadUtil;

    private final static String DEFAULT_AVATAR_ID = "24e0a7fc-91c7-4ad6-9a2e-43991fab1591";

    @Override
    public List<CustomerAvatar> findAll() {
        return customerAvatarRepository.findAll();
    }

    @Override
    public Optional<CustomerAvatar> findById(String id) {
        return customerAvatarRepository.findById(id);
    }

    @Override
    public CustomerAvatar save(CustomerAvatar customerAvatar) {
        return customerAvatarRepository.save(customerAvatar);
    }

    @Override
    public void delete(CustomerAvatar customerAvatar) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public CustomerAvatar createCustomerAvatar(MultipartFile avatar, Customer customer) {
        CustomerAvatar customerAvatar = new CustomerAvatar();
        customerAvatar.setCustomer(customer);
        customerAvatarRepository.save(customerAvatar);
        uploadAndSaveCustomerAvatar(avatar, customerAvatar);
        return customerAvatar;
    }

    @Override
    public Optional<CustomerAvatar> findCustomerAvatarByCustomerIdAndDeletedIsFalse(String id) {
        return customerAvatarRepository.findCustomerAvatarByCustomerIdAndDeletedIsFalse(id);
    }

    private void uploadAndSaveCustomerAvatar(MultipartFile avatar, CustomerAvatar customerAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(avatar, cloudinaryUploadUtil.buildImageUploadParams(customerAvatar.getId(), cloudinaryUploadUtil.CUSTOMER_IMAGE_UPLOAD_FOLDER, cloudinaryUploadUtil.ERROR_IMAGE_UPLOAD));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            customerAvatar.setFileName(customerAvatar.getId() + "." + fileFormat);
            customerAvatar.setFileUrl(fileUrl);
            customerAvatar.setFileFolder(cloudinaryUploadUtil.CUSTOMER_IMAGE_UPLOAD_FOLDER);
            customerAvatar.setCloudId(customerAvatar.getFileFolder() + "/" + customerAvatar.getId());
            customerAvatarRepository.save(customerAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }
}
