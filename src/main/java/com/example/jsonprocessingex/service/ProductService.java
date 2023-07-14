package com.example.jsonprocessingex.service;

import com.example.jsonprocessingex.model.dto.ProductInRangeDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void seedProducts() throws IOException;

    List<ProductInRangeDto> getProductsInRange();
}
