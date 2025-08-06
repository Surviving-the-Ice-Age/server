package com.iceAge.server.instagram_post.infrastructure.repository;

import com.iceAge.server.instagram_post.domain.model.InstagramPost;
import com.iceAge.server.instagram_post.domain.repository.InstagramPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InstagramPostRepositoryImpl implements InstagramPostRepository {

    private final InstagramPostJpaRepository instagramPostJpaRepository;

    @Override
    public InstagramPost save(InstagramPost instagramPost) {
        return instagramPostJpaRepository.save(instagramPost);
    }

    @Override
    public Optional<InstagramPost> findByPostId(String postId) {
        return instagramPostJpaRepository.findByPostId(postId);
    }

    @Override
    public boolean existsByPostId(String postId) {
        return instagramPostJpaRepository.existsByPostId(postId);
    }
}