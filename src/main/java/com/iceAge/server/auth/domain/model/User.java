package com.iceAge.server.auth.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String providerId;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String nickname;

    @Builder
    public User(SocialProvider socialProvider, Role role, String providerId, String email, String nickname) {
        this.socialProvider = socialProvider;
        this.role = role;
        this.providerId = providerId;
        this.email = email;
        this.nickname = nickname;
    }
}
