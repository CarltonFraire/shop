package com.example.shop.services;

import com.example.shop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.example.shop.entity.Product;
import com.example.shop.exception.BusinessException;
import com.example.shop.repository.ProductRepository;
import com.example.shop.validator.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) { // 构造器注入
        this.productRepository = productRepository;
    }



    @Transactional//全部执行否则全不执行
    public Product  createProduct(ProductDto productDto) {
        Product product = new Product();

        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setImage(productDto.getImage());
        product.setStock(productDto.getStock());
        product.setDescription(productDto.getDescription());
        product.setStatus(0);
        return productRepository.save(product);

    }

    @Transactional
    public Product updateProduct(Long id,ProductDto productDto) {
        Product product = getProductByIdOrThrow(id);

        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        product.setStock(productDto.getStock());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        return productRepository.save(product);
    }
    public  Product getProductByIdOrThrow(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new BusinessException(" 未找到相应产品ID："+id));
    }
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("产品未找到，ID: " + id));
        productRepository.delete(product);
    }

    public List<Product> getAllProducts() {
            return productRepository.findAll();

    }
    public Page<Product> getAllProductsWithPaging(int page, int size) {
        if (page < 0) throw new BusinessException("页码不能小于0");
        if (size <= 0) throw new BusinessException("每页数量必须大于0");

        PageRequest pageRequest = PageRequest.of(page, size);
        return (Page<Product>) productRepository.findAll(pageRequest);
    }
    }