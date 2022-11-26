package com.example.security.config;

import com.example.security.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
1. 코드 받기 (인증)
2. 엑세스 토큰 (권한)
3. 사용자 프로필 정보를 가져와서
4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (vip등급, 일반등급)
*/
@Configuration
@EnableWebSecurity // by enabling this annotation security filter is registered to spring filter chain
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //  secured 어노테이션 활성화 // preAuthorize라는 어노테이션 활성화
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    // 해당메서드의 리턴되는 오브젝트는 IoC에 등록해준다
    @Bean
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
   public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .requestMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // /login 주소가 호출되면 스프링 시큐리티가 낚아 채서 대신 로그인을 진행
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
                // google login이 완료된 뒤의 후처리가 필요함
                /*
                Tip. 코드 X, (엑세스토큰 + 사용자 프로필정보 O)
                */
                .and().and()
                .build();
    }

}
