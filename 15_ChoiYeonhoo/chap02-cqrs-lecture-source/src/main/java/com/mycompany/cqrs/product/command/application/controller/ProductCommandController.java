package com.mycompany.cqrs.product.command.application.controller;

import com.mycompany.cqrs.common.dto.ApiResponse;
import com.mycompany.cqrs.product.command.application.dto.request.ProductCreateRequest;
import com.mycompany.cqrs.product.command.application.dto.request.ProductUpdateRequest;
import com.mycompany.cqrs.product.command.application.dto.response.ProductCommandResponse;
import com.mycompany.cqrs.product.command.application.service.ProductCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/* 데이터 변경 명령 담당 (Command Side)*/
@RestController
@RequiredArgsConstructor
public class ProductCommandController {

  private final ProductCommandService productCommandService;

  /* 상품 등록 */
  @PostMapping("/products")
  public ResponseEntity<ApiResponse<ProductCommandResponse>> createProduct(
      @RequestPart @Validated ProductCreateRequest request, @RequestPart MultipartFile productImg){

    /* 전달 받은 request의 데이터를 이용해
    * DB에 새 데이터 삽입 후, 삽입된 행의 PK(productCode 반환 받기
    * */
    Long productCode = productCommandService.createProduct(request,productImg);

    ProductCommandResponse response = ProductCommandResponse.builder()
        .productCode(productCode)
        .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
  }

  /* 상품 수정 */
  @PutMapping("/products/{productCode}")
  public ResponseEntity<ApiResponse<Void>> updateProduct(
      @PathVariable("productCode") Long productCode,
      @RequestPart @Validated ProductUpdateRequest request,
      @RequestPart(required = false) MultipartFile productImg
      ){

    productCommandService.updateProduct(
        productCode,
        request,
        productImg
    );

    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @DeleteMapping("/products/{productCode}")
  public ResponseEntity<ApiResponse<Void>> deleteProduct(
      @PathVariable Long productCode){
    productCommandService.deleteProduct(productCode);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null));
  }

}
