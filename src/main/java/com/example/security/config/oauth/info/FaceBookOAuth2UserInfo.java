package com.example.security.config.oauth.info;

import java.util.Map;

public class FaceBookOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public FaceBookOAuth2UserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getPassword() {
        return (String) attributes.get("password");
    }

    @Override
    public String getProvider() {
        return "facebook";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("providerId");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
