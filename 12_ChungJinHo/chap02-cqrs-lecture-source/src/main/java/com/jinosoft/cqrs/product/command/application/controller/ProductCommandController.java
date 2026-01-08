package com.jinosoft.cqrs.product.command.application.controller;

import com.jinosoft.cqrs.common.dto.ApiResponse;
import com.jinosoft.cqrs.product.command.application.dto.request.ProductCreateRequest;
import com.jinosoft.cqrs.product.command.application.dto.request.ProductUpdateRequest;
import com.jinosoft.cqrs.product.command.application.dto.response.ProductCommandResponse;
import com.jinosoft.cqrs.product.command.application.service.ProductCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

@RestController
@RequiredArgsConstructor
public class ProductCommandController {

  private final ProductCommandService productCommandService;

  @PostMapping("/products")
  public ResponseEntity<ApiResponse<ProductCommandResponse>> createProduct(
      @RequestPart("productCreateRequest") @Validated ProductCreateRequest productCreateRequest,
      @RequestPart("productImg") MultipartFile productImg
  ){

    /* 전달 받은 ProductCreateRequest의 데이터를 이용해
     * DB에 새 데이터 삽입 후 삽입된 행의 PK(productCode) 반환 받기
     * */
    Long productCode
        = productCommandService.createProduct(productCreateRequest, productImg);

    ProductCommandResponse response
        = ProductCommandResponse.builder()
        .productCode(productCode)
        .build();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success(response));
  }

  @PutMapping("/products/{productCode}")
  public ResponseEntity<ApiResponse<Void>> updateProduct(
      @PathVariable("productCode") Long productCode,
      @RequestPart @Validated ProductUpdateRequest productUpdateRequest,
      @RequestPart(required = false) MultipartFile productImg
  ) {
    productCommandService.updateProduct(productCode,productUpdateRequest,productImg);

    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @DeleteMapping("/products/{productCode}")
  public ResponseEntity<ApiResponse<Void>> deleteProduct(
      @PathVariable("productCode") Long productCode
  ){
    productCommandService.deleteProduct(productCode);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null));
  }

}
