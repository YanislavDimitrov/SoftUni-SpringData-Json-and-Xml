package com.example.jsonprocessingex;

import com.example.jsonprocessingex.constants.GlobalConstant;
import com.example.jsonprocessingex.model.dto.ProductInRangeDto;
import com.example.jsonprocessingex.service.CategoryService;
import com.example.jsonprocessingex.service.ProductService;
import com.example.jsonprocessingex.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.jsonprocessingex.constants.GlobalConstant.RESOURCE_FILE_PATH;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    public static final String PRODUCTS_IN_RANGE_FILE = "products-in-range.json";
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final Gson gson;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
    }


    @Override
    public void run(String... args) throws Exception {
        seedData();

        String jsonString = gson
                .toJson(this.productService.getProductsInRange(), ProductInRangeDto[].class);

        Files.writeString(Path.of(RESOURCE_FILE_PATH + PRODUCTS_IN_RANGE_FILE), jsonString);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
