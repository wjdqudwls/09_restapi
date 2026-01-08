package com.wjdqudwls.springsecurity.command.repository;

import com.wjdqudwls.springsecurity.command.dto.UserCreateRequest;
import com.wjdqudwls.springsecurity.command.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);


}
