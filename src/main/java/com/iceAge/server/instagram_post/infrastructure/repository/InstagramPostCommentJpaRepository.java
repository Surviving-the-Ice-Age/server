package com.iceAge.server.instagram_post.infrastructure.repository;

import com.iceAge.server.instagram_post.domain.model.InstagramPost;
import com.iceAge.server.instagram_post.domain.model.InstagramPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstagramPostCommentJpaRepository extends JpaRepository<InstagramPostComment, Long> {

    /**
     * 특정 InstagramPost의 모든 댓글을 조회합니다.
     */
    List<InstagramPostComment> findByInstagramPost(InstagramPost instagramPost);

    /**
     * commentId로 댓글을 조회합니다.
     */
    Optional<InstagramPostComment> findByCommentId(String commentId);

    /**
     * commentId가 존재하는지 확인합니다.
     */
    boolean existsByCommentId(String commentId);
}