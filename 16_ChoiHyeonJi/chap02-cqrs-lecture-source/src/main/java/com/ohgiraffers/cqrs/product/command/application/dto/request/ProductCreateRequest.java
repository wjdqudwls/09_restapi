package com.ohgiraffers.cqrs.product.command.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // 필수!!(final 붙어있는 필드 초기화) 매개변수 생성자
public class ProductCreateRequest {
  @NotBlank
  private final String productName;

  @Min(value = 1) // 1보다는 커야한다 최소값이 1
  private final Long productPrice;

  @NotBlank
  private final String productDescription;

  @Min(value = 1)
  private final Long categoryCode;

  @Min(value = 1)
  private final Long productStock;
}
