package com.example.security.controller;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // view를 리턴
public class IndexController {

    @Autowired
    UserRepository userRepository;

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
    @GetMapping({"/user"})
    public @ResponseBody String user(){
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
    public @ResponseBody String join(User user){
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        System.out.println(user);
        user.setRole("ROLE_USER");
        userRepository.save(user); // -> 이렇게 회원가입하면 나중에 시큐리티로 로그인을 할 수 없음 왜냐면 패스워드 암호화가 안되었기 때뭉에
        return "join";
    }
    @GetMapping({"/logout"})
    public @ResponseBody String logout() {
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "logout";
    }

}

