package com.example.shop.validator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDto {

    @NotBlank(message = "请上传商品名字")
    private String name;

    @NotBlank(message = "请上传商品介绍")
    private String description;

    @NotNull(message = "请上传商品价格")
    @Positive(message = "商品价格必须为正数")
    private BigDecimal price;

    @NotBlank(message = "请上传商品图片")
    private String image;

    @NotNull(message = "请上传库存数量")
    @Positive(message = "库存数量必须为正数")
    private Integer stock;
}