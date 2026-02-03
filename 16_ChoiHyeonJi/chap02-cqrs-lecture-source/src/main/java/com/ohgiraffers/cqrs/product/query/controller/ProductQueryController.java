package com.ohgiraffers.cqrs.product.query.controller;

import com.ohgiraffers.cqrs.common.dto.ApiResponse;
import com.ohgiraffers.cqrs.product.query.dto.request.ProductSearchRequest;
import com.ohgiraffers.cqrs.product.query.dto.response.ProductDetailResponse;
import com.ohgiraffers.cqrs.product.query.dto.response.ProductListResponse;
import com.ohgiraffers.cqrs.product.query.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* 조회 담당 컨트롤러 (Query Side) */

// @ResponseBody + @Controller
@RestController // Rest : 자원 이름을 이용해서 http 상태 코드만 넣어  insert delete 하는 거
@RequiredArgsConstructor
public class ProductQueryController {
  private final ProductQueryService productQueryService;

  @GetMapping("/products/{productCode}")
  public ResponseEntity<ApiResponse<ProductDetailResponse>> getProduct
      (@PathVariable("productCode") Long proudctCode) {


    ProductDetailResponse response =
        productQueryService.getProduct(proudctCode);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /* 조건에 맞는 상품 목록 조회 */
  @GetMapping("/products")
  public ResponseEntity<ApiResponse<ProductListResponse>> getProducts(
     // @ModelAttribute (생략 가능)
     ProductSearchRequest productSearchRequest
  ){
    ProductListResponse response = productQueryService.getProducts(productSearchRequest);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
