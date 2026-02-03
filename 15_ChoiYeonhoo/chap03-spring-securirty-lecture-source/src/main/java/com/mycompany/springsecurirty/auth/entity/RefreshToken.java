package com.mycompany.springsecurirty.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "refresh_token")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

  @Id
  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String token;

  @Column(nullable = false)
  private Date expiryDate;
}
