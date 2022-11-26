package com.example.security.config.auth;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
시큐리티 설정에서 loginProcessingUrl("/login")
요청이 들어오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 메서드 실행
* */
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    // Security Session -> Authentication -> UserDetails
    // Security Session (내부 Authentication (내부 UserDetails) )
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUserName(username);

        if(userEntity !=null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
