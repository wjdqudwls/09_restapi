package com.wjdqudwls.cqrs.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Pagination {

  private final int currentPage;
  private final int totalPage;
  private final long totalItems;
}
