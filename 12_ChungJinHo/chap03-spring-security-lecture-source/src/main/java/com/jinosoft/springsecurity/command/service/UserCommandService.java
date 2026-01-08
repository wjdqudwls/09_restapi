package com.jinosoft.springsecurity.command.service;

import com.jinosoft.springsecurity.command.dto.UserCreateRequest;
import com.jinosoft.springsecurity.command.entity.User;
import com.jinosoft.springsecurity.command.repository.UserReposotiry;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {
  private final UserReposotiry userReposotiry;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void registUser(UserCreateRequest userCreateRequest){
    User user = modelMapper.map(userCreateRequest, User.class);
    user.setEncodedPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
    userReposotiry.save(user);
  }

}
