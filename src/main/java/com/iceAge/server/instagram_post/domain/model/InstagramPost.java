package com.iceAge.server.instagram_post.domain.model;

import com.iceAge.server.global.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "instagram_post")
public class InstagramPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String postId;

    @Column
    private Long views; // 조회수

    @Column
    private Long likes; // 좋아요 수

    @Column
    private Long comments; // 댓글 수

    @Column
    private Long saved; // 저장 수
}
