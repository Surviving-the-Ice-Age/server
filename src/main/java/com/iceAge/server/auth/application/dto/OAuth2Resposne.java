package com.iceAge.server.auth.application.dto;

public interface OAuth2Resposne {

    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();
}
