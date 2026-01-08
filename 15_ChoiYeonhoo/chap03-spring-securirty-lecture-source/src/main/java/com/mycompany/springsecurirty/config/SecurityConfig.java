package com.mycompany.springsecurirty.config;

import com.mycompany.springsecurirty.jwt.JwtAuthenticationFilter;
import com.mycompany.springsecurirty.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // @PreAuthorize, @PostAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  /* Spring Security와 연결된 설정 객체 */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

    // CSRF 비 활성화 처리, 기본값 활성화
    // JWT는 세션 이용 X (Stateless) -> CSRF 보호가 필수적이지 않음
    http.csrf(AbstractHttpConfigurer::disable)
        // 세션 로그인 X -> 토큰 로그인
        // 세션을 생성하지 않고, SecurityContextHolder에서 session 저장 X
        // -> 모든 요청에 독립적, 인증 정보는 클라이언트 요청 시 전달된 토큰에 의지함
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 요청 http method, url 기준으로 인증, 인가 필요 여부를 설정
        .authorizeHttpRequests(auth
            // 회원 가입,로그인은 누구나 허용
            -> auth
            .requestMatchers(HttpMethod.POST,"/api/v1/users","api/v1/auth/login").permitAll()
            // 내 정보 조회는 유저 권한이 필요
            .requestMatchers(HttpMethod.GET,"api/v1/users/me").hasAuthority("USER")
            // 위 요청을 제외한 나머지 요청은 인증이 필요함.
            .anyRequest().authenticated()
        )
        // UsernamePasswordAuthenticationFilter 앞에 
        // JWT 인증 커트텀 필터를 추가
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;

    return http.build();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(){
    return new JwtAuthenticationFilter(jwtTokenProvider,userDetailsService);
  }

}
