package com.ohgiraffers.springsecurity.command.controller;

import com.ohgiraffers.springsecurity.command.dto.UserCreateRequest;
import com.ohgiraffers.springsecurity.command.service.UserCommandService;
import com.ohgiraffers.springsecurity.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserCommandController {
  private final UserCommandService userCommandService;

  /* 회원 가입 */ // POST 기록해서 붙임
  @PostMapping("/users")
  public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserCreateRequest userCreateRequest){

    // 서비스 호출
    userCommandService.registUser(userCreateRequest); // 201 Create (삽입/저장 성공)
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(null));
  }


}
