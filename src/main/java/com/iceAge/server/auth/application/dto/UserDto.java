package com.iceAge.server.auth.application.dto;

import com.iceAge.server.auth.domain.model.Role;
import com.iceAge.server.auth.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class UserDto {

    private Role role;
    private String nickname;
    private String username;
    private String email;


    @Builder
    public UserDto(Role role, String nickname, String username, String email) {
        this.role = role;
        this.nickname = nickname;
        this.username = username;
        this.email = email;
    }

    public static UserDto from(User user) {
        return UserDto.builder()
                .role(user.getRole())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
