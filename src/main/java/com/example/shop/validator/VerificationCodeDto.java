package com.example.shop.validator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerificationCodeDto {
    @NotBlank(message = "无效的邮箱")
    @Email(message = "无效的邮箱")
    private String email;
}
