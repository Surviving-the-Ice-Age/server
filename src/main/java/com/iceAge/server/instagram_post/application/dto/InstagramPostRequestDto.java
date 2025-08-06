package com.iceAge.server.instagram_post.application.dto;

import java.util.List;

public class InstagramPostRequestDto {

    private final List<String> imageUrls;
    private final String caption;

    public InstagramPostRequestDto(List<String> imageUrls, String caption) {
        this.imageUrls = imageUrls;
        this.caption = caption;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public String getCaption() {
        return caption;
    }
}