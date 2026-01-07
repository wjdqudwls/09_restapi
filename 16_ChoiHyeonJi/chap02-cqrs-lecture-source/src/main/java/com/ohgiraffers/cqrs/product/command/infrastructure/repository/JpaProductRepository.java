package com.ohgiraffers.cqrs.product.command.infrastructure.repository;


import com.ohgiraffers.cqrs.product.command.domain.aggregate.Product;
import com.ohgiraffers.cqrs.product.command.domain.aggregate.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository
    extends JpaRepository<Product, Long>, ProductRepository {


}
