package com.example.dreamshops.dto;

import com.example.dreamshops.model.Product;
import com.example.dreamshops.dto.ProductDto;

import java.math.BigDecimal;

public class CartItemDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}