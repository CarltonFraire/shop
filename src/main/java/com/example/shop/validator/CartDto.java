package com.example.shop.validator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CartDto {
    @NotNull(message="请输入商品id")
    private Long productId;
    @NotBlank(message="请输入商品数量")
    @Positive(message="数量必须是正数")
    private String quantity;
    @NotBlank(message="请输入用户id")
    private String username;
}
