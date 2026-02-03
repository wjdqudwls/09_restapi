package com.wjdqudwls.cqrs.product.query.dto.response;

import com.wjdqudwls.cqrs.common.dto.Pagination;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.LongStream;

@Getter
@Builder
public class ProductListResponse {

  private final List<ProductDTO> products;
  private final Pagination pagination;
}
