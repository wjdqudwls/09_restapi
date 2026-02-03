package com.ohgiraffers.springsecurity.command.service;

import com.ohgiraffers.springsecurity.command.dto.UserCreateRequest;
import com.ohgiraffers.springsecurity.command.entity.User;
import com.ohgiraffers.springsecurity.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  // 가입할 수 있게 DB에 save하는 코드
  @Transactional
  public void registUser(UserCreateRequest userCreateRequest){
    /* request(DTO) -> Entity */
    User user = modelMapper.map(userCreateRequest, User.class);

    /* 비밀번호 암호화하여 Entity에 세팅 */
    user.setEncodedPassword(passwordEncoder.encode(userCreateRequest.getPassword())); //요청에 담겨있는 password 가져와서 변환 (인코더)하고 유저에 세팅

    /* 저장 */
    userRepository.save(user);

  }

}
