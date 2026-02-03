package com.mycompany.springsecurirty.command.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role = UserRole.USER;


  /* 암호화된 비밀번호 세팅하는 메서드 */
  public void setEncodedPassword(String encodedPassword){
    this.password = encodedPassword;
  }

  public void modifyRole(String role) {
    this.role = UserRole.valueOf(role);
  }
}
