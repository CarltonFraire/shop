package com.example.shop.controller;


import org.springframework.data.domain.Page;
import com.example.shop.R;
import com.example.shop.entity.Product;
import com.example.shop.exception.BusinessException;
import com.example.shop.services.ProductService;
import com.example.shop.validator.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public R createProduct(@Valid @RequestBody ProductDto productDto) throws JsonProcessingException {
        Product createdProduct = productService.createProduct(productDto);
        return R.ok("创建产品", createdProduct);
    }

    @PutMapping("/{id}")
    public R updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        Product updatedProduct = productService.updateProduct(id, productDto);
        return R.ok("更新产品", updatedProduct);
    }

    @GetMapping("/{id}")
    public R getProductById(@PathVariable Long id) {
        Product product = productService.getProductByIdOrThrow(id);
        return R.ok("获取产品ID", product);
    }

    @GetMapping
    public R getAllProducts() {
            List<Product> products = productService.getAllProducts();
            return R.ok("获取商品列表成功", products);

    }

    @GetMapping("/page")
    public R getProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Product> productPage = productService.getAllProductsWithPaging(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("list", productPage.getContent());
        response.put("total", productPage.getTotalElements());

            return R.ok("分页获取成功", response);

    }

    @DeleteMapping("/{id}")
    public R deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return R.ok("删除产品");
    }
}