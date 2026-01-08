package com.jinosoft.springsecurity.query.service;

import com.jinosoft.springsecurity.query.dto.UserDTO;
import com.jinosoft.springsecurity.query.dto.UserDetailResponse;
import com.jinosoft.springsecurity.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryService {
  private final UserMapper userMapper;

  public UserDetailResponse getUserDeatil(String username) {
    UserDTO user = Optional.ofNullable(userMapper.findUserByUsername(username))
        .orElseThrow(() -> new RuntimeException("사용자 찾기 실패"));

    return UserDetailResponse.builder()
        .user(user)
        .build();
  }
}
