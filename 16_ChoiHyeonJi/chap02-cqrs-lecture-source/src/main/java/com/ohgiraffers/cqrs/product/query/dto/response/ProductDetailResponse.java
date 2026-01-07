package com.ohgiraffers.cqrs.product.query.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // 빌더 패턴을 이용한 객체 생성 코드 추가 어노테이션
public class ProductDetailResponse { // 일관된응답의 모습으로 나타남

  /* API 응답 구조의 일관성(항상 response로 응답)과 코드 확장성
  * JSON 데이터 응답 형태를 위해
  * DTO를 감싼 Reponse 클래스를 별도 생성
  * */
  private final ProductDTO product;
  //private final int likeCount; // product dto 건들지않고 이부분만 건들면됨
}
