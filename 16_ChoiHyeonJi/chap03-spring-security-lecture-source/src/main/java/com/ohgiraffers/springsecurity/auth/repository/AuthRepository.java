package com.ohgiraffers.springsecurity.auth.repository;

import com.ohgiraffers.springsecurity.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<RefreshToken, String> { // Id 어노테이션에 적힌 타입 적으면 됨 == String
}
