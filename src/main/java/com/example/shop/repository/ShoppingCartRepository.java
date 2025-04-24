package com.example.shop.repository;

import com.example.shop.entity.ShoppingCart;
import com.example.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);

    Optional<ShoppingCart> findByUserId(Long userId);
}

