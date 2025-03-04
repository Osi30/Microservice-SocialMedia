package com.nam.image_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("type")
    private String type;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("public_name")
    private String publicName;

    @JsonProperty("is_thumbnail")
    private String isThumbnail;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("post_id")
    private String postId;
}
