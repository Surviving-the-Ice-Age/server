package com.iceAge.server.instagram_post.infrastructure.repository;

import com.iceAge.server.instagram_post.domain.model.InstagramPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstagramPostJpaRepository extends JpaRepository<InstagramPost, Long> {

    Optional<InstagramPost> findByPostId(String postId);

    boolean existsByPostId(String postId);
}