package com.jinosoft.springsecurity.auth.repository;

import com.jinosoft.springsecurity.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<RefreshToken, String> {
}
