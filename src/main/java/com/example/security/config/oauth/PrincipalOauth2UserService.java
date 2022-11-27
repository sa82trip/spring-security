package com.example.security.config.oauth;

import com.example.security.config.auth.PrincipalDetails;
import com.example.security.config.oauth.info.FaceBookOAuth2UserInfo;
import com.example.security.config.oauth.info.GoogleOAuth2UserInfo;
import com.example.security.config.oauth.info.OAuth2UserInfo;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    /* 메서드 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다*/
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.printf("userRequest: %s%n", userRequest.getClientRegistration()); // registraionId로 어떤 OAuth로 로그인 했는지 알 수 있음
        System.out.printf("userRequest: %s%n", userRequest.getAccessToken().getTokenValue());
        // 유저가 구글로 로긴 버튼 클릭 -> google login 화면 -> login complete -> we receive code from google(OAuth-client library) -> access token 요청
        // userRequest을 통해서 ->  loadUser method 호출 -> 구글로부터 유저의 프로필 받음
        // * we need to get profile of the person who is trying to login
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.printf("getAttributes: %s%n", super.loadUser(userRequest).getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if("google".equals(userRequest.getClientRegistration().getRegistrationId())){
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        }
        if("facebook".equals(userRequest.getClientRegistration().getRegistrationId())){
            oAuth2UserInfo = new FaceBookOAuth2UserInfo(oAuth2User.getAttributes());
        }
        // 여기서 받은 정보로 회원가입을 강제로 진행
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("authnoneedpassword");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUserName(username);
        if(userEntity == null) {
            System.out.println("구글 로그인이 최초입니다.");
            userEntity = User.builder()
                    .userName(username)
                    .email(email)
                    .password(password)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        } else {
            //
            System.out.println("구글 로그인을 이미 한 적이 있습니다. ");
        }

        //return super.loadUser(userRequest);
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes()); // 이제 이 객체는 spring security session의 Authentication에 들어가게 된다
    }
}
