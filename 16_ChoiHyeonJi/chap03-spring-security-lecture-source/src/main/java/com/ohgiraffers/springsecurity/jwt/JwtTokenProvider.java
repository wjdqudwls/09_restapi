package com.ohgiraffers.springsecurity.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


@Component // @Bean 등록
public class JwtTokenProvider { // 토큰 생성기

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private long jwtExpiration;

  @Value("${jwt.refresh-expiration}")
  private long jwtRefreshExpiration;

  private SecretKey secretKey;

  @PostConstruct
  public void init() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret); //인코딩 = 압축 decode 압축 푼다
    secretKey = Keys.hmacShaKeyFor(keyBytes); // 암호화방식 넣어줌
  }
  /* access token 생성 메서드 */
  public String createToken(String username, String role) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpiration);
    // JWT 토큰 생성 -> Header, Payload(Claims), Signiture
    return Jwts.builder()
        .subject(username) // payload: subject (보통 사용자 식별) (등록 claim)
        .claim("role", role) // payload: role (권한 정보) (비공개 claim)
        .issuedAt(now)          // payload: IssuedAt (발생 시간)
        .expiration(expiryDate) // payload: Expiraion Time (만료 시간)
        .signWith(secretKey)    // signature: 비밀키 서명(위변조 방지용)
        .compact(); // 헤더가 없다??? ㄴ == 헤더는 이미 hmacShaKeyFor여기에 정보가 저장되어있음

  }

  /* refresh token 생성 메서드 */
  public String createRefreshToken(String username, String role) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtRefreshExpiration);
    // JWT 토큰 생성 -> Header, Payload(Claims), Signiture
    return Jwts.builder()
        .subject(username) // payload: subject (보통 사용자 식별) (등록 claim)
        .claim("role", role) // payload: role (권한 정보) (비공개 claim)
        .issuedAt(now)          // payload: IssuedAt (발생 시간)
        .expiration(expiryDate) // payload: Expiraion Time (만료 시간)
        .signWith(secretKey)    // signature: 비밀키 서명(위변조 방지용)
        .compact();
  }

  // refresh token 만료 시간 반환
  public long getRefreshExpiration() {
    return jwtRefreshExpiration;
  }

  /* JWT 토큰 유효성 검사 메서드 */
  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      throw new BadCredentialsException("Invalid JWT Token", e);
    } catch (ExpiredJwtException e) {
      throw new BadCredentialsException("Expired JWT Token", e);
    } catch (UnsupportedJwtException e) {
      throw new BadCredentialsException("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException("JWT Token claims empty", e);
    }

  }

  /* JWT 토큰 중 payload -> claim -> subject의 값 반환 */
  public String getUsernameFromJWT(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claims.getSubject();
  }
}
