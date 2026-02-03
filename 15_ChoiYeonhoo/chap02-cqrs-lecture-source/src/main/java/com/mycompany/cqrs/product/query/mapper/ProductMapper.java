package com.mycompany.cqrs.product.query.mapper;

import com.mycompany.cqrs.product.query.dto.request.ProductSearchRequest;
import com.mycompany.cqrs.product.query.dto.response.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // mybatis 매퍼 등록 -> 구현된 프록시 객체가 빈으로 등록
public interface ProductMapper {

  /* 상품 코드로 상품 상세 조회 */
  ProductDTO selectProductByCode(Long productCode);

  /* 조건에 맞는 상품 개수 조회 */
  long countProducts(ProductSearchRequest productSearchRequest);

  /* 조건에(검색/페이징) 맞는 상품 목록 조회 */
  List<ProductDTO> selectProducts(ProductSearchRequest productSearchRequest);
  }

