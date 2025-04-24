package com.example.shop.validator;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShoppingCartDto {
    @NotBlank(message = "产品id不能为空")
    private String productId;

    @NotBlank(message = "产品数量不能为空")
    private String stock;

    @NotBlank(message = "产品价格不能为空")
    private String price;

    @NotBlank(message = "用户名不能为空")
    private String username;

    public Long getUserId() {
        return Long.parseLong(username);
    }

    public Long getQuantity() {
        return Long.parseLong(stock);
    }
}
