package com.wjdqudwls.springsecurity.query.mapper;

import com.wjdqudwls.springsecurity.query.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  UserDTO findUserByUsername(String username);
}
