package com.mycompany.springsecurirty.auth.service;

import com.mycompany.springsecurirty.auth.dto.LoginRequest;
import com.mycompany.springsecurirty.auth.dto.TokenResponse;
import com.mycompany.springsecurirty.auth.entity.RefreshToken;
import com.mycompany.springsecurirty.auth.repository.AuthRepository;
import com.mycompany.springsecurirty.command.entity.User;
import com.mycompany.springsecurirty.command.entity.UserRepository;
import com.mycompany.springsecurirty.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {


  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthRepository authRepository;

  /* 로그인 */
  public TokenResponse login(LoginRequest request){
    // 1. ID(username)로 조회 -> id(username),pwd(암호화) 조회됨
    User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(()-> new BadCredentialsException(" 아이디 또는 비밀번호가 일치하지 않습니다."));

    // 2. 비밀 번호 매칭 확인
    if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
      throw new BadCredentialsException(" 아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    // 3. 비밀 번호 일치 -> 로그인 성공 -> 토큰 발급
    String accessToken = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name());
    String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(), user.getRole().name());

    // 4. Refresh Token 저장(보안 및 토큰 재발급 검증 용)
    RefreshToken tokenEntity = RefreshToken.builder()
        .username(user.getUsername())
        .token(refreshToken)
        .expiryDate(new Date(System.currentTimeMillis() + jwtTokenProvider.getRefreshExpiration()))
        .build();

    authRepository.save(tokenEntity);

    return TokenResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  /* refresh token 검증 후 새 토큰 발급 서비스 */
  public TokenResponse refreshToken(String provideRefreshToken) {

    // refresh token 유효성 검사
    jwtTokenProvider.validateToken(provideRefreshToken);

    // 전달 받은 refresh token에서 사용자 이름(username) 얻어오기
    String username = jwtTokenProvider.getUsernameFromJWT(provideRefreshToken);

    // DB에서 username이 일치하는 행의 refresh token 조회
    RefreshToken storedToken = authRepository.findById(username)
        .orElseThrow(()-> new BadCredentialsException("해당 유저로 조회되는 refresh 토큰 없음"));

    // 요청 시 전달 받은 token과
    // DB에 저장 된 토큰이 일치하는지 확인
    if(!storedToken.getToken().equals(provideRefreshToken)){
      throw new BadCredentialsException("refresh token 이 일치하지 않음");
    }

    // 요청 시 전달 받은 token의 만료 기간이 현재 시간보다 과거인지 확인
    // (만료 기간이 지났는지 확인)
    if(storedToken.getExpiryDate().before(new Date())){
      throw new BadCredentialsException("refresh token 이 만료됨");
    }

    // username이 일치하는 회원(User) 조회
    User user = userRepository.findByUsername(username)
        .orElseThrow(()->new BadCredentialsException("해당 유저 없음"));

    // 새 토큰 발급
    String accessToken = jwtTokenProvider.createToken(user.getUsername(),user.getRole().name());
    String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(),user.getRole().name());

    // RefreshToken 엔티티 생성(저장용)
    RefreshToken tokenEntity = RefreshToken.builder()
        .username(username)
        .token(refreshToken)
        .expiryDate(new Date(System.currentTimeMillis() + jwtTokenProvider.getRefreshExpiration()))
        .build();

    //DB 저장 (PK 행이 이미 존재 -> UPDATE)
    authRepository.save(tokenEntity);

    return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }

  /* DB refreshToken 삭제 (로그 아웃) */
  public void logout(String refreshToken) {

    /* refresh Token 검증 */
    jwtTokenProvider.validateToken(refreshToken);

    String username = jwtTokenProvider.getUsernameFromJWT(refreshToken);
    
    authRepository.deleteById(username); // DB 에서 username이 일치하는 행 삭제
  }
}
