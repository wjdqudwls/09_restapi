package com.mycompany.springsecurirty.auth.repository;

import com.mycompany.springsecurirty.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<RefreshToken, String> {

}
