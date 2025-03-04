package com.nam.image_service.controller;

import com.nam.gateway.annotation.GatewayOnly;
import com.nam.image_service.dto.ImageDTO;
import com.nam.image_service.service.ImageService;
import com.nam.image_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@GatewayOnly
public class ImageController {
    private final ImageService imageService;
    private final UserService userService;

    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> uploadImage(
            @RequestHeader("Authorization") String token,
            @RequestParam("file") MultipartFile file,
            @RequestParam("isThumbnail") String isThumbnail,
            @RequestParam("postId") String postId
    ) throws IOException {
        ImageDTO imageRequest = ImageDTO.builder()
                .userId(userService.getUserProfileByToken(token).getId())
                .postId(postId)
                .isThumbnail(isThumbnail)
                .build();
        ImageDTO image = imageService.uploadImage(file, imageRequest);
        return new ResponseEntity<>(image, HttpStatus.ACCEPTED);
    }
}
