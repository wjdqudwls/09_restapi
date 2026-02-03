package com.wjdqudwls.restapi.section04.hateoas;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



  @Setter
  @Getter
  @AllArgsConstructor
  @ToString
public class UserDTO {

  private int no;
  private String id;
  private String pwd;
  private String name;
  private Date enrollDate;
}
