package com.mycompany.springsecurirty.command.service;

import com.mycompany.springsecurirty.command.dto.UserCreateRequest;
import com.mycompany.springsecurirty.command.entity.User;
import com.mycompany.springsecurirty.command.entity.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void registUser(UserCreateRequest request){

    /* request(DTO) -> Entity */
    User user = modelMapper.map(request,User.class);

    /* 비밀번호를 암호화 하여 Entity에 세팅 */
    user.setEncodedPassword(
        passwordEncoder.encode(request.getPassword())
    );

    /* 저장 */
    userRepository.save(user);
  }

}
