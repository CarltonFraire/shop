package com.example.shop.validator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "无效的邮箱")
    private String email;

    @NotBlank(message = "验证码不能为空")
    private String verificationCode;


    @NotBlank(message = "用户名不能为空")
    private String name;

    @Min(value = 8, message = "不能小于八位的密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
