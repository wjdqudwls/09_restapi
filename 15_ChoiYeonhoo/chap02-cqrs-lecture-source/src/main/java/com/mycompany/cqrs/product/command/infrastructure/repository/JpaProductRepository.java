package com.mycompany.cqrs.product.command.infrastructure.repository;

import com.mycompany.cqrs.product.command.domain.aggreagete.Product;
import com.mycompany.cqrs.product.command.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product,Long>, ProductRepository {

}
