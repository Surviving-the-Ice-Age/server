package com.iceAge.server.auth.application.dto;

import com.iceAge.server.auth.domain.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private String role;
    private String name;
    private String username;
    private String email;


    @Builder
    public UserDto(String role, String name, String username, String email) {
        this.role = role;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public static UserDto from(User user) {
        return UserDto.builder()
                .role(user.getRole().toString())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
