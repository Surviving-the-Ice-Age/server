package com.iceAge.server.auth.domain.model;

import com.iceAge.server.auth.application.exception.NOT_SUPPORTED_SOCIAL_PROVIDER;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialProvider{
    GOOGLE("google");

    private final String socialProviderName;

    public static SocialProvider from(String socialProviderName) {
        for (SocialProvider provider : values()) {
            if (provider.getSocialProviderName().equalsIgnoreCase(socialProviderName)) {
                return provider;
            }
        }
        throw new NOT_SUPPORTED_SOCIAL_PROVIDER(socialProviderName);
    }
}
