package com.jinosoft.springsecurity.command.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {
  private final String username;
  private final String password;


}
