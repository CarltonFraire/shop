package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private BigDecimal snapshotPrice;

    @ManyToOne
    @JsonIgnore
    private ShoppingCart cart;

    @Transient
    public BigDecimal getSubtotal() {
        return snapshotPrice.multiply(BigDecimal.valueOf(quantity));
    }
    public CartItem(ShoppingCart cart, Product product, Long quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = Math.toIntExact(quantity);
    }
    public CartItem() {}
}
