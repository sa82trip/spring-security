package com.example.security.config.auth;

import com.example.security.model.User;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행
로그인 진행이 완료가 되면 시큐리티 session을 만든다 (Security ContextHolder)
오브젝트타입 -> Authentication 타입 객체
Authentication 안에 User 정보가 있어야 됨.
User 오브젝트타입 -> UserDetails 타입 객체
* */
// Security Session -> Authentication -> UserDetails(PrincipalDetails)
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user){
       this.user = user;
    }

    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 사이트에 1년 동안 로그인을 안하면 휴면으로 돌리는 설정이 필요할 때
        return true;
    }
}
