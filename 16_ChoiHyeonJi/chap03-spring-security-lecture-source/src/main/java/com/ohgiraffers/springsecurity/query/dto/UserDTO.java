package com.ohgiraffers.springsecurity.query.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 있어도 됨
public class UserDTO {
  private String username;
  private String role;
}
