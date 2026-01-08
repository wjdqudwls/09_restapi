package com.mycompany.springsecurirty.command.controller;


import com.mycompany.springsecurirty.command.dto.UserCreateRequest;
import com.mycompany.springsecurirty.command.service.UserCommandService;
import com.mycompany.springsecurirty.common.ApiResponse;
import com.mycompany.springsecurirty.query.dto.UserDTO;
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

  /* 회원 가입 */
  @PostMapping("/users")
  public ResponseEntity<ApiResponse<Void>> register(
      @RequestBody UserCreateRequest request){

    userCommandService.registUser(request);

   return ResponseEntity
       .status(HttpStatus.CREATED) // 201 Create(삽입/저장) 성공
       .body(ApiResponse.success(null));

  }
}
