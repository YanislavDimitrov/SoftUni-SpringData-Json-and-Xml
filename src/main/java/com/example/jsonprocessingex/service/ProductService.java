package com.example.jsonprocessingex.service;

import com.example.jsonprocessingex.model.dto.ProductInRangeDto;

import java.io.IOException;

public interface ProductService {
    void seedProducts() throws IOException;

    ProductInRangeDto[] getProductsInRange();
}
