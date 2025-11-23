package com.github.cidarosa.product_catalog.repository;

import com.github.cidarosa.product_catalog.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
