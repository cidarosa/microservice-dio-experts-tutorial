package com.github.cidarosa.shopping_cart.repository;

import com.github.cidarosa.shopping_cart.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
