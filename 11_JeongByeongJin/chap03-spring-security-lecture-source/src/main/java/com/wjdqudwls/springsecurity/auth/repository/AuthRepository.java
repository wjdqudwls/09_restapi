package com.wjdqudwls.springsecurity.auth.repository;


import com.wjdqudwls.springsecurity.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<RefreshToken, String> {

}
