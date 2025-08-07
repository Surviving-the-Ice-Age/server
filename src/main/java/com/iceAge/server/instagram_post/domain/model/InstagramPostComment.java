package com.iceAge.server.instagram_post.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "post_comment", indexes = {
        @Index(name = "idx_post_id", columnList = "postId")
})
public class InstagramPostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String commentId; // 댓글 ID (인스타그램 제공 고유 식별자)

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private InstagramPost instagramPost; // 댓글이 달린 게시글

    @Column
    private String comments; // 댓글 내용

    public static InstagramPostComment createComment(String commentId, InstagramPost instagramPost, String comments) {
        return InstagramPostComment.builder()
                .commentId(commentId)
                .instagramPost(instagramPost)
                .comments(comments)
                .build();
    }
}
