package com.example.shop.services;

import com.example.shop.entity.*;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.ShoppingCartRepository;
import com.example.shop.validator.ShoppingCartDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserService userService;  // 依赖注入

    public ShoppingCart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userService.findUserById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }
    @Transactional
    public ShoppingCart addItem(ShoppingCartDto dto) {
        // 从DTO提取数据
        User user = userService.findUserById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        Product product = productService.getProductByIdOrThrow(Long.valueOf(dto.getProductId()));
        Long quantity = dto.getQuantity();


        ShoppingCart cart = getOrCreateCart(user.getId());
        cartItemRepository.findByCartAndProduct(cart, product)
                .ifPresentOrElse(
                        item -> item.setQuantity((int) (item.getQuantity() + quantity)),
                        () -> {
                            CartItem newItem = new CartItem(cart, product, quantity);
                            cartItemRepository.save(newItem);
                            cart.getItems().add(newItem);
                        }
                );

        return cartRepository.save(cart);
    }


    @Transactional
    public void removeItem(Long userId, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (userId.equals(item.getCart().getUser().getId())) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        cartItemRepository.delete(item);
    }
}
