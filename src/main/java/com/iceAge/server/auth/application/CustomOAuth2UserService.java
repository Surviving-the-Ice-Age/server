package com.iceAge.server.auth.application;

import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import com.iceAge.server.auth.application.dto.GoogleResponse;
import com.iceAge.server.auth.application.dto.NaverResponse;
import com.iceAge.server.auth.application.dto.OAuth2Resposne;
import com.iceAge.server.auth.application.dto.UserDto;
import com.iceAge.server.auth.domain.model.Role;
import com.iceAge.server.auth.domain.model.User;
import com.iceAge.server.auth.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Resposne oAuth2Response = getOAuth2Response(registrationId, oAuth2User);

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        User user = userRepository.findByUsername(username)
                .map(existingUser -> updateIfNecessary(existingUser, oAuth2Response))
                .orElseGet(() -> registerNewUser(username, oAuth2Response));

        return new CustomOAuth2User(UserDto.from(user));
    }

    private OAuth2Resposne getOAuth2Response(String registrationId, OAuth2User oAuth2User) {
        return switch (registrationId) {
            case "naver" -> new NaverResponse(oAuth2User.getAttributes());
            case "google" -> new GoogleResponse(oAuth2User.getAttributes());
            default -> throw new IllegalArgumentException("지원하지 않는 소셜 로그인: " + registrationId);
        };
    }

    private User registerNewUser(String username, OAuth2Resposne response) {
        User newUser = User.builder()
                .username(username)
                .email(response.getEmail())
                .name(response.getName())
                .role(Role.USER)
                .build();
        return userRepository.save(newUser);
    }

    private User updateIfNecessary(User user, OAuth2Resposne response) {
        user.updateEmail(response.getEmail());
        user.updateName(response.getName());
        return userRepository.save(user);
    }

}