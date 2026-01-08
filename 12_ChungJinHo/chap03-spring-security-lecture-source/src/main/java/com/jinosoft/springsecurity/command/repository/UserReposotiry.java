package com.jinosoft.springsecurity.command.repository;

import com.jinosoft.springsecurity.command.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReposotiry extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
