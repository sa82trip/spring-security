package com.example.security.controller;

import com.example.security.config.auth.PrincipalDetails;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // view를 리턴
public class IndexController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, //DI 의존성 주입을 이용
                                          //@AuthenticationPrincipal UserDetails userDetails
                                          @AuthenticationPrincipal PrincipalDetails userDetails)
    {
        System.out.println("/test/login =================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); //DI 의존성 주입을 이용
        System.out.println("authentication: "+ principalDetails.getUser());
        //System.out.println("userDetails: " + userDetails.getUsername());
        System.out.println("userDetails: " + userDetails.getUser());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication, /*DI 의존성 주입을 이용*/
            @AuthenticationPrincipal OAuth2User oAuth )
    {
        System.out.println("/test/oauth/login =================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal(); //DI 의존성 주입을 이용
        System.out.println("authentication: "+ oAuth2User.getAttributes());
        //System.out.println("userDetails: " + userDetails.getUsername());
        System.out.println("oauth2User: " + oAuth.getAttributes());
        return "oauth 세션 정보 확인하기";
    }

    @GetMapping({"","/"})
    public String index(){
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "index";
    }
    @GetMapping({"/manager"})
    public @ResponseBody String manager(){
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "manager";
    }
    @GetMapping({"/admin"})
    public @ResponseBody String admin(){
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "admin";
    }
    // OAuth 로그인을 해도 PrincipalDetails
    // 일반 로그인을해도 PrincipalDetails
    @GetMapping({"/user"})
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("PrincipalDetails: "+ principalDetails.getUser());
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "user";
    }
    @GetMapping({"/loginForm"}) // spring security가 intercept 했었는데, SecurityConfig 설저 후 안낚아챔
    public String login() {
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "loginForm";
    }
    @GetMapping({"/joinForm"}) // spring security가 intercept 했었는데, SecurityConfig 설저 후 안낚아챔
    public String joinForm() {
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "joinForm";
    }
    @PostMapping({"/join"})
    public String join(User user){
        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user); // -> 이렇게 회원가입하면 나중에 시큐리티로 로그인을 할 수 없음 왜냐면 패스워드 암호화가 안되었기 때뭉에
        System.out.println(user);
        return "redirect:/loginForm";
    }
    @GetMapping({"/logout"})
    public @ResponseBody String logout() {
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "logout";
    }

    @Secured("ROLE_ADMIN") // securityConfig에서 Global설정 한 것으로 인해서 이런 설정이 가능
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보:";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }

}

