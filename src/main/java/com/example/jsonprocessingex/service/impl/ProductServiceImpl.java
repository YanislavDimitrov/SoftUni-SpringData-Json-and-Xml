package com.example.jsonprocessingex.service.impl;

import com.example.jsonprocessingex.model.dto.ProductInRangeDto;
import com.example.jsonprocessingex.model.dto.ProductSeedDto;
import com.example.jsonprocessingex.model.entity.Category;
import com.example.jsonprocessingex.model.entity.Product;
import com.example.jsonprocessingex.repository.ProductRepository;
import com.example.jsonprocessingex.service.CategoryService;
import com.example.jsonprocessingex.service.ProductService;
import com.example.jsonprocessingex.service.UserService;
import com.example.jsonprocessingex.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.jsonprocessingex.constants.GlobalConstant.RESOURCE_FILE_PATH;

@Service
public class ProductServiceImpl implements ProductService {
    public static final String PRODUCTS_FILE_NAME = "products.json";

    private final Gson gson;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(Gson gson, CategoryService categoryService, UserService userService, ValidationUtil validationUtil, ModelMapper modelMapper, ProductRepository productRepository) {
        this.gson = gson;
        this.categoryService = categoryService;
        this.userService = userService;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public void seedProducts() throws IOException {
        if (productRepository.count() > 0) {
            return;
        }
        String jsonContent = Files.readString(Path.of(RESOURCE_FILE_PATH + PRODUCTS_FILE_NAME));

        Arrays.stream(gson.fromJson(jsonContent, ProductSeedDto[].class))
                .filter(validationUtil::isValid)
                .map(p -> modelMapper.map(p, Product.class))
                .map(p -> {
                    p.setCategories(categoryService.getRandomCategories());
                    p.setSeller(userService.getRandomUser());
                    p.setBuyer(p.getName().length() % 2 == 0 ? null : userService.getRandomUser());
                    return p;
                })
                .forEach(productRepository::save);
    }

    @Override
    public List<ProductInRangeDto> getProductsInRange() {
        return modelMapper.map(this.productRepository
                .findAllByPriceBetweenAndBuyerNull(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L)),
                new TypeToken<List<ProductInRangeDto>>(){}.getType());
    }
}
