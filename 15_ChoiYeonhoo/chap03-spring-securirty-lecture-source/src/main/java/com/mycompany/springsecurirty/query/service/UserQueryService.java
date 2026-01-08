package com.mycompany.springsecurirty.query.service;

import com.mycompany.springsecurirty.common.ApiResponse;
import com.mycompany.springsecurirty.query.dto.UserDTO;
import com.mycompany.springsecurirty.query.dto.UserDetailResponse;
import com.mycompany.springsecurirty.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

  private final UserMapper userMapper;

  public UserDetailResponse getUserDetail(String username) {
    UserDTO user = Optional.ofNullable(
        userMapper.findUserByUsername(username)
    ).orElseThrow(()->new RuntimeException("사용자 찾지 못함"));

    return UserDetailResponse.builder().user(user).build();
  }
}
