package com.iceAge.server.instagram_post.domain.repository;

import com.iceAge.server.instagram_post.domain.model.InstagramPost;
import java.util.Optional;

public interface InstagramPostRepository {

    InstagramPost save(InstagramPost instagramPost);

    Optional<InstagramPost> findByPostId(String postId);

    boolean existsByPostId(String postId);
}