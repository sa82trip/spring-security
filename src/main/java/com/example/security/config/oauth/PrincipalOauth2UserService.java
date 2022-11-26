package com.example.security.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    //구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.printf("userRequest: %s%n", userRequest.getClientRegistration());
        System.out.printf("userRequest: %s%n", userRequest.getAccessToken().getTokenValue());
        System.out.printf("userRequest: %s%n", super.loadUser(userRequest).getAttributes());

        // 여기서 받은 정보로 회원가입을 강제로 진행
        return super.loadUser(userRequest);
    }
}
