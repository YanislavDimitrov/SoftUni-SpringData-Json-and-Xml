package com.example.jsonprocessingex;

import com.example.jsonprocessingex.model.dto.*;
import com.example.jsonprocessingex.model.dto.problem4.ProductsCountDto;
import com.example.jsonprocessingex.model.dto.problem4.UsersCountDto;
import com.example.jsonprocessingex.service.CategoryService;
import com.example.jsonprocessingex.service.ProductService;
import com.example.jsonprocessingex.service.UserService;
import com.example.jsonprocessingex.util.FormatConverter;
import com.example.jsonprocessingex.util.FormatConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    public static final String PRODUCTS_IN_RANGE_FILE_NAME = "1-products-in-range";
    public static final String USERS_WITH_SOLD_PRODUCT_FILE_NAME = "2-users-with-sold-product";
    public static final String CATEGORIES_BY_PRODUCT_COUNT_FILE_NAME = "3-categories-by-product-count";
    public static final String USERS_WITH_SOLD_PRODUCTS_ORDERED_FILE_NAME = "4-users-with-sold-product-ordered";
    public static final String RESOURCES_OUTPUT_PATH = "output/";
    public static final String RESOURCES_FILES_PATH = "src/main/resources/files/";
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final FormatConverterFactory formatConverterFactory;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService,
                                 UserService userService,
                                 ProductService productService,
                                 FormatConverterFactory factory) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.formatConverterFactory = factory;
    }


    @Override
    public void run(String... args) throws Exception {
        seedData();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter format (XML/JSON): ");
        String formatInput = sc.nextLine();
        System.out.println("Please enter desired output (console/file): ");
        String outputType = sc.nextLine();
        System.out.println("Please enter command:" +
                "\n1 -> Get all products in price range 500 to 1000, which have no buyer" +
                "\n2 -> Get all users who have at least 1 item sold" +
                "\n3 -> Get all categories with products count, average price and total revenue" +
                "\n4 -> Get all users (with their products) who have at least 1 item sold ordered by products sold");
        int command = Integer.parseInt(sc.nextLine());

        FormatConverter converter = formatConverterFactory.getConverter(formatInput);
        converter.setPrettyPrinting();
        switch (command) {
            case 1:
                List<ProductInRangeDto> productsInRange = this.productService.getProductsInRange();

                ProductsCollection productsToSerialize =
                        new ProductsCollection(productsInRange);

                if (outputType.equalsIgnoreCase("console")) {
                    System.out.println(converter.serialize(productsToSerialize));
                } else if (outputType.equalsIgnoreCase("file")) {
                    converter.serialize(productsToSerialize,
                            RESOURCES_FILES_PATH +
                                    RESOURCES_OUTPUT_PATH +
                                    PRODUCTS_IN_RANGE_FILE_NAME +
                                    "." + formatInput);
                    System.out.println("Output file created in " + RESOURCES_FILES_PATH + RESOURCES_OUTPUT_PATH);
                } else {
                    System.out.println("Invalid Output type.");
                }
                break;
            case 2:
                List<UserSoldProductsDto> usersWithSoldProduct = this.userService.getAllUsersWithSoldProduct();

                UsersWithSoldProduct usersToSerialize = new UsersWithSoldProduct(usersWithSoldProduct);

                if (outputType.equalsIgnoreCase("console")) {
                    System.out.println(converter.serialize(usersToSerialize));
                } else if (outputType.equalsIgnoreCase("file")) {
                    converter.serialize(usersToSerialize,
                            RESOURCES_FILES_PATH +
                                    RESOURCES_OUTPUT_PATH +
                                    USERS_WITH_SOLD_PRODUCT_FILE_NAME +
                                    "." + formatInput);
                    System.out.println("Output file created in " + RESOURCES_FILES_PATH + RESOURCES_OUTPUT_PATH);
                } else {
                    System.out.println("Invalid Output type.");
                }
                break;
            case 3:
                List<CategorySummaryDto> categoriesSummary = this.categoryService.getCategoriesSummary();
                CategorySummaries categorySummaries = new CategorySummaries(categoriesSummary);

                if (outputType.equalsIgnoreCase("console")) {
                    System.out.println(converter.serialize(categorySummaries));
                } else if (outputType.equalsIgnoreCase("file")) {
                    converter.serialize(categorySummaries,
                            RESOURCES_FILES_PATH +
                                    RESOURCES_OUTPUT_PATH +
                                    CATEGORIES_BY_PRODUCT_COUNT_FILE_NAME +
                                    "." + formatInput);
                    System.out.println("Output file created in " + RESOURCES_FILES_PATH + RESOURCES_OUTPUT_PATH);
                } else {
                    System.out.println("Invalid Output type.");
                }
                break;
            case 4:
                UsersCountDto productsCountDto = this.userService.getAllUsersWithSoldProductOrderBySoldProducts();

                if (outputType.equalsIgnoreCase("console")) {
                    System.out.println(converter.serialize(productsCountDto));
                } else if (outputType.equalsIgnoreCase("file")) {
                    converter.serialize(productsCountDto,
                            RESOURCES_FILES_PATH +
                                    RESOURCES_OUTPUT_PATH +
                                    USERS_WITH_SOLD_PRODUCTS_ORDERED_FILE_NAME +
                                    "." + formatInput);
                    System.out.println("Output file created in " + RESOURCES_FILES_PATH + RESOURCES_OUTPUT_PATH);
                } else {
                    System.out.println("Invalid Output type.");
                }
                break;
        }
    }


    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
