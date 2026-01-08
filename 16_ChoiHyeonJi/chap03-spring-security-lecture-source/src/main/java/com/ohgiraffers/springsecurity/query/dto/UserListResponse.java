package com.ohgiraffers.springsecurity.query.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserListResponse { // 유저 목록
  private List<UserDTO> users;
}
