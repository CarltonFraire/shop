package com.example.shop.services;

import com.example.shop.entity.Order;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.exception.BusinessException;
import com.example.shop.repository.OrderRepository;
import com.example.shop.validator.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;



@Service
public  class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService) {
      this.orderRepository = orderRepository;
      this.userService = userService;
      this.productService = productService;
    }
    @Transactional//全部执行否则全不执行
    public Order createOrder(OrderDto orderDto) {
        User username = userService.findByUsernameOrThrow(orderDto.getUsername());
        Product productId = productService.getProductByIdOrThrow(orderDto.getProductId());

        Order order = new Order();
        order.setProductId(productId);
        order.setUsername(username);


        return orderRepository.save(order);

    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("订单未找到，ID: " + id));
    }
   @Transactional
   public void deleteOrder(Long id) {
       Order order = orderRepository.findById(id)
               .orElseThrow(() -> new BusinessException("订单未找到，ID: " + id));
       orderRepository.delete(order);
    }


}
