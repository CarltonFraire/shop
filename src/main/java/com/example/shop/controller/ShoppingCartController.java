package com.example.shop.controller;

import com.example.shop.R;
import com.example.shop.validator.ShoppingCartDto;
import com.example.shop.entity.ShoppingCart;
import com.example.shop.services.ShoppingCartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class ShoppingCartController {
    private final ShoppingCartService cartService;


    public ShoppingCartController(ShoppingCartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping("/users/{userId}")
    public R getCart(@PathVariable Long userId) {
        ShoppingCart cart = cartService.getOrCreateCart(userId);
        return R.ok("购物车获取成功", cart);
    }


    @PostMapping("/items")
    public R addItem(@Valid @RequestBody ShoppingCartDto cartDto) {
        ShoppingCart updatedCart = cartService.addItem(cartDto);
        return R.ok("商品添加成功", updatedCart);
    }


    @DeleteMapping("/items/{itemId}/users/{userId}")
    public R removeItem(@PathVariable Long itemId, @PathVariable Long userId) {
        cartService.removeItem(userId, itemId);
        return R.ok("商品删除成功");
    }
}
