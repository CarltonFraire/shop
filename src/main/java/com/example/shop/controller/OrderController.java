package com.example.shop.controller;

import com.example.shop.R;
import com.example.shop.entity.Order;
import com.example.shop.services.OrderService;
import com.example.shop.validator.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public R createOrder(@Valid @RequestBody OrderDto orderDto) throws JsonProcessingException {
        Order createOrder = orderService.createOrder(orderDto);
        return R.ok("创建订单", createOrder);
    }
    @GetMapping("/{id}")
    public R getProductById(@PathVariable Long id) {
        Order product = orderService.findById(id);
        return R.ok("获取订单产品", product);
    }
    @DeleteMapping("/{id}")
    public R deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return R.ok("删除订单");
    }

}
