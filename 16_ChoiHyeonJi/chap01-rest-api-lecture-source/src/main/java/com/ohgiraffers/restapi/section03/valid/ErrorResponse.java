package com.ohgiraffers.restapi.section03.valid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponse {

  /* 예외 발생 시 에러 메세지를 전달하는 용도의 객체*/

  // 에러코드 몇번인지 자세한 내용 담아서 전달함.
    private String code;
    private String description;
    private String detail;

}