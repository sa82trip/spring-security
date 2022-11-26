package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // view를 리턴
public class IndexController {

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
    @GetMapping({"/join"})
    public @ResponseBody String join(){
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "join";
    }
    @GetMapping({"/logout"})
    public @ResponseBody String logout() {
        // 머스테치 기본촐터 src/main/resources/
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix)
        return "logout";
    }

}

