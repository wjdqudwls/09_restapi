package com.mycompany.springsecurirty.query.mapper;

import com.mycompany.springsecurirty.query.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  UserDTO findUserByUsername(String username);
}
