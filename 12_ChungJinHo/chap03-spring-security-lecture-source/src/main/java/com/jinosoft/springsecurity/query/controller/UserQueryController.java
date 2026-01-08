package com.jinosoft.springsecurity.query.controller;

import com.jinosoft.springsecurity.common.ApiResponse;
import com.jinosoft.springsecurity.query.dto.UserDTO;
import com.jinosoft.springsecurity.query.dto.UserDetailResponse;
import com.jinosoft.springsecurity.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserQueryController {

  private final UserQueryService userQueryService;

  @GetMapping("/users/me")
  public ResponseEntity<ApiResponse<UserDetailResponse>> getUserDetail(
      @AuthenticationPrincipal UserDetails userDetails
      ){
    UserDetailResponse response
        = userQueryService.getUserDeatil(userDetails.getUsername());

    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
