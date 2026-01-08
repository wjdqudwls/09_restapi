package com.jinosoft.springsecurity.auth.service;

import com.jinosoft.springsecurity.auth.dto.LoginRequest;
import com.jinosoft.springsecurity.auth.dto.TokenResponse;
import com.jinosoft.springsecurity.auth.entity.RefreshToken;
import com.jinosoft.springsecurity.auth.repository.AuthRepository;
import com.jinosoft.springsecurity.command.entity.User;
import com.jinosoft.springsecurity.command.repository.UserReposotiry;
import com.jinosoft.springsecurity.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserReposotiry userReposotiry;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthRepository authRepository;

  public TokenResponse login(LoginRequest loginRequest){
    User user = userReposotiry.findByUsername(loginRequest.getUsername())
        .orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다."));

    if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    String accessToken = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name());
    String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(), user.getRole().name());

    RefreshToken tokenEntity = RefreshToken.builder()
        .username(user.getUsername())
        .token(refreshToken)
        .expiryDate(new Date(System.currentTimeMillis() + jwtTokenProvider.getJwtRefreshExpirationTime()))
        .build();

    authRepository.save(tokenEntity);




    return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }
}
