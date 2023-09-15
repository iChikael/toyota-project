package com.cg.cloudinary;

import com.cg.exception.DataInputException;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class CloudinaryUploadUtil {

    public final String STAFF_IMAGE_UPLOAD_FOLDER = "toyota_car/staff_avatar";
    public final String CUSTOMER_IMAGE_UPLOAD_FOLDER = "toyota_car/customer_avatar";
    public final String CAR_IMAGE_UPLOAD_FOLDER = "toyota_car/car_avatar";

    public final String IDENTIFICATION_IMAGE_UPLOAD_FOLDER = "toyota_car/identification_image";

    public final String ERROR_IMAGE_UPLOAD = "Không thể upload hình ảnh của sản phẩm chưa được lưu.";
    public final String ERROR_IMAGE_DESTROY = "Không thể destroy hình ảnh của sản phẩm không xác định.";

    public Map buildImageUploadParams(String id, String imageFolder, String errorMessage) {
        if (id == null)
            throw new DataInputException(errorMessage);

        String publicId = String.format("%s/%s", imageFolder, id);

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageDestroyParams(String id, String publicId, String errorMessage) {
        if (id == null)
            throw new DataInputException(errorMessage);

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

}

