package com.iceAge.server.auth.domain.model;

import com.iceAge.server.analysis.domain.model.Promotion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String name;

    @OneToOne
    @Setter
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Builder
    public User(Role role, String username, String email, String name) {
        this.role = role;
        this.username = username;
        this.email = email;
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}
