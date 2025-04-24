package com.example.shop.repository;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(ShoppingCart cart, Product product);
}
