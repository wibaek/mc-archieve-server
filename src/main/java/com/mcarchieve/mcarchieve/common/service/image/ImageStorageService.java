package com.mcarchieve.mcarchieve.common.service.image;

import com.mcarchieve.mcarchieve.common.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    Image storeImage(MultipartFile file, FileUploadPath path);

}
