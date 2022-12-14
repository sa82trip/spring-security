package com.example.security.repository;

import com.example.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD는 jpsrepo가 가지고 있음
// this is already spring bean because jpaRepo is spring bean
public interface UserRepository extends JpaRepository<User, Integer> {
    //findBy규칙->userName 문법
    // select * from user where username = 1?
    User findByUserName(String userName); // jpa query method
}
