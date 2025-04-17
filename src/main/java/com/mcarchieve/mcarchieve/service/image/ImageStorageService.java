package com.mcarchieve.mcarchieve.service.image;

import com.mcarchieve.mcarchieve.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    Image storeImage(MultipartFile file, FileUploadPath path);

}
