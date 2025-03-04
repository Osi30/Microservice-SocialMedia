package com.nam.image_service.mapper;

import com.nam.image_service.dto.ImageDTO;
import com.nam.image_service.entity.Image;

public interface ImageMapper {
    ImageDTO toImageDTO(Image image);
}
