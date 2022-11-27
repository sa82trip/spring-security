package com.example.security.config.oauth.info;

public interface OAuth2UserInfo {
    String getName();
    String getPassword();
    String getProvider();
    String getProviderId();
    String getEmail();
}
