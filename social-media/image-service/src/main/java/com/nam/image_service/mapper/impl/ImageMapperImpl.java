package com.nam.image_service.mapper.impl;

import com.nam.image_service.dto.ImageDTO;
import com.nam.image_service.entity.Image;
import com.nam.image_service.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageDTO toImageDTO(Image image) {
        return ImageDTO.builder()
                .id(image.getId())
                .title(image.getTitle())
                .type(image.getType())
                .imageUrl(image.getImageUrl())
                .publicName(image.getPublicName())
                .isThumbnail(image.getIsThumbnail().toString())
                .postId(image.getPostId())
                .userId(image.getUserId())
                .build();
    }
}
