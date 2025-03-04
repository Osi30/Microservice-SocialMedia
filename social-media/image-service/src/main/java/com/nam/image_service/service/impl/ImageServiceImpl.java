package com.nam.image_service.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nam.image_service.dto.ImageDTO;
import com.nam.image_service.entity.Image;
import com.nam.image_service.exception.ImageException;
import com.nam.image_service.mapper.ImageMapper;
import com.nam.image_service.repository.ImageRepository;
import com.nam.image_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@EnableAsync
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final Cloudinary cloudinary;
    private final List<String> allowedContentTypes = Arrays.asList("image/jpeg", "image/png", "image/gif");

    @Override
    public ImageDTO uploadImage(MultipartFile file, ImageDTO imageRequest) throws ImageException, IOException {
        validateFile(file);
        String publicValue = generatePublicValue(file.getOriginalFilename());
        String extension = getFilename(Objects.requireNonNull(file.getOriginalFilename()))[1];
        File fileUpload = convert(file);
        String filePath = uploadToCloudinaryAsync(fileUpload, publicValue, extension);
        cleanDisk(fileUpload);
        Image savedImage = imageRepository.save(Image.builder()
                .title(file.getOriginalFilename())
                .type(file.getContentType())
                .publicName(publicValue)
                .imageUrl(filePath)
                .isThumbnail(imageRequest.getIsThumbnail() != null && imageRequest.getIsThumbnail().equals("true"))
                .postId(imageRequest.getPostId())
                .userId(imageRequest.getUserId())
                .build());
        return imageMapper.toImageDTO(savedImage);
    }

    protected String uploadToCloudinaryAsync(File file, String publicValue, String extension) {
        try {
            cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", publicValue));
            return cloudinary.url().generate(StringUtils.join(publicValue, ".", extension));
        } catch (Exception e) {
            throw new ImageException(e.getMessage());
        }
    }

    private File convert(MultipartFile file) throws IOException {
        File convertedFile = new File(StringUtils.join(
                generatePublicValue(file.getOriginalFilename()),
                getFilename(Objects.requireNonNull(file.getOriginalFilename()))[1])
        );
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, convertedFile.toPath());
        }
        return convertedFile;
    }

    private String generatePublicValue(String originalFilename) {
        String filename = getFilename(originalFilename)[0];
        return StringUtils.join(StringUtils.join(UUID.randomUUID().toString(), "_", filename));
    }

    private String[] getFilename(String originalFilename) {
        return originalFilename.split("\\.");
    }

    @Async
    protected void cleanDisk(File file) {
        try {
            Path filePath = file.toPath();
            Files.delete(filePath);
            CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            CompletableFuture.failedFuture(new ImageException(e.getMessage()));
        }
    }

    private void validateFile(MultipartFile file) throws ImageException {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageException("File is null or empty");
        }
        if (!allowedContentTypes.contains(file.getContentType())) {
            throw new ImageException("File type is not allowed: " + file.getContentType());
        }
    }
}
