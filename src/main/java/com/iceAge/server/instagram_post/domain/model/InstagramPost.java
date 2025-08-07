package com.iceAge.server.instagram_post.domain.model;

import com.iceAge.server.analysis.domain.model.Promotion;
import com.iceAge.server.auth.domain.model.User;
import com.iceAge.server.global.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "instagram_post")
public class InstagramPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne(mappedBy = "instagramPost")
    private Promotion promotion;

    @Column(nullable = false, unique = true, updatable = false)
    private String postId;

    @Column
    private Long views; // 조회수

    @Column
    private Long likes; // 좋아요 수

    @Column
    private Long commentsCount; // 댓글 수

    @Column
    private Long saved; // 저장 수

    @Column
    private LocalDate insightsStartDate; // 인사이트 시작 날짜

    @Column
    private LocalDate insightsEndDate; // 인사이트 종료 날짜

    public void updateInstagramPostFromInsights(Long views, Long likes, Long comments, Long saved) {
        this.views = views;
        this.likes = likes;
        this.commentsCount = comments;
        this.saved = saved;
        this.insightsStartDate = LocalDate.now();
        this.insightsEndDate = LocalDate.now();
    }

    public void updateInsightsDates(LocalDate startDate, LocalDate endDate) {
        this.insightsStartDate = startDate;
        this.insightsEndDate = endDate;
    }

    public void addPromotion(Promotion promotion){
        this.promotion = promotion;
        promotion.setInstagramPost(this);
    }

}
