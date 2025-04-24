package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "`t_product`")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    private String name;
    private BigDecimal price;
    private String image;
    private Integer stock;
    private String description;

    @Column(columnDefinition = "integer default 0")
    // 0 表示未上架 1表示上架中
    private Integer status = 0;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orderList;
}