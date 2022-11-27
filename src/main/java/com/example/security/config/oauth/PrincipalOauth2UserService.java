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
        System.out.printf("userRequest: %s%n", userRequest.getClientRegistration()); // registraionId로 어떤 OAuth로 로그인 했는지 알 수 있음
        System.out.printf("userRequest: %s%n", userRequest.getAccessToken().getTokenValue());
        // 유저가 구글로 로긴 버튼 클릭 -> google login 화면 -> login complete -> we receive code from google(OAuth-client library) -> access token 요청
        // userRequest을 통해서 ->  loadUser method 호출 -> 구글로부터 유저의 프로필 받음
        // * we need to get profile of the person who is trying to login
        System.out.printf("userRequest: %s%n", super.loadUser(userRequest).getAttributes());

        // 여기서 받은 정보로 회원가입을 강제로 진행
        return super.loadUser(userRequest);
    }
}
