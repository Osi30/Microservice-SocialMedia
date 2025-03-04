package com.nam.image_service.service;

import com.nam.image_service.dto.ImageDTO;
import com.nam.image_service.exception.ImageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ImageDTO uploadImage(MultipartFile file, ImageDTO imageRequest) throws ImageException, IOException;
}
