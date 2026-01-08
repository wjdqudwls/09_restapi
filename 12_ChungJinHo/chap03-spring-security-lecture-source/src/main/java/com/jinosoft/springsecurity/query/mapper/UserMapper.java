package com.jinosoft.springsecurity.query.mapper;

import com.jinosoft.springsecurity.query.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  UserDTO findUserByUsername(String username);
}
