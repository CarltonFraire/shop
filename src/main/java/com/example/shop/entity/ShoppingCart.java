package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "`t_shopping_cart`")
public class ShoppingCart {

    @Id
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();


}



