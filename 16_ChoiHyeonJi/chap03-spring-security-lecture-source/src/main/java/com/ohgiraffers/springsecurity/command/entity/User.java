package com.ohgiraffers.springsecurity.command.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor //jpa 엔티티 만들떄 꼭 기본생성자 필요
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false) //매핑 허용xx 유니크 중복 값이 안되고
  private String username;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING) // 글자로 저장
  @Column(nullable = false)
  private UserRole role = UserRole.USER; //enum만들기


  /* 암호화된 비밀번호를 세팅하는 메서드 */ // 암호화 -> 크립토라는 곳에서 받아야함 (시큐리티에 포함됨)
  public void setEncodedPassword(String encodedPassword) {
    this.password = encodedPassword;
  }

}
