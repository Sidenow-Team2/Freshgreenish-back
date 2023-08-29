package com.sidenow.freshgreenish.domain.product.repository;

import com.sidenow.freshgreenish.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {
}
