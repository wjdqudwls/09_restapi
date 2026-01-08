package com.mycompany.cqrs.product.command.domain.repository;

import com.mycompany.cqrs.product.command.domain.aggreagete.Product;

import java.util.Optional;

public interface ProductRepository {

  Product save(Product product);

  Optional<Product> findById(Long productCode);

  void deleteById(Long productCode);
}
