package com.ohgiraffers.springsecurity.query.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDetailResponse { // 상세 조회
  private UserDTO user;
}
