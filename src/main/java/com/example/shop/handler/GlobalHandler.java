package com.example.shop.handler;

import com.example.shop.R;
import com.example.shop.exception.BusinessException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMsg = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMsg.append(fieldName).append(": ").append(errorMessage).append("\n ");
        });
        return R.error(errorMsg.toString());
    }

    @ExceptionHandler(BusinessException.class)
    public R handleBusinessExceptions(BusinessException ex) {
        return R.error(ex.getMessage());
    }

//    @ExceptionHandler(RuntimeException.class)
//    public R handleRuntimeExceptions(BusinessException ex) {
//        return R.error("未知错误");
//    }
}
