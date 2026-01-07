package com.ohgiraffers.cqrs.common.dto;

/* 공통 응답 처리 클래스
* - API 응답 형식을 통일해서
* 클라이언트가 일관된 방식으로 응답을 처리할 수 있도록 함. */
// 응답의 형태가 항상 일관되게 할거임.

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiResponse<T> {

  private boolean success; // 요청 성공 여부를 t/f
  private T data; // 실제 데이터 (성공 시 사용) // T 제네릭 쓰려면 <T> 다이아몬드 연산자 써야함
  private String errorCode; // 에러 코드(실패 시 사용)
  private String message; // 에러 메시지(실패)
  private LocalDateTime timestamp; // 응답 생성 시간

  /* 성공 응답 생성 정적 메서드 */
  public static <T> ApiResponse<T> success(T data){
    return ApiResponse.<T>builder()
        .success(true)
        .data(data)
        .timestamp(LocalDateTime.now())
        .build();
  }

  /* 실패 응답 생성 정적 메서드 */
  public static <T> ApiResponse<T> failure(String erroCode, String message){
    return ApiResponse.<T>builder()
        .success(false)
        .errorCode(erroCode)
        .message(message)
        .timestamp(LocalDateTime.now())
        .build();
  }

}
