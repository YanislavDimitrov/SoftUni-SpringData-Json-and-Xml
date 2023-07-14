package com.example.jsonprocessingex;

import com.example.jsonprocessingex.model.dto.*;
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

import static com.example.jsonprocessingex.constants.GlobalConstant.RESOURCE_FILE_PATH;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    public static final String PRODUCTS_IN_RANGE_FILE_NAME = "products-in-range";
    public static final String USERS_WITH_SOLD_PRODUCT_FILE_NAME = "users-with-sold-product";
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
                "\n2 -> Get all users who have at least 1 item sold");
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
                System.out.println(converter.serialize(categorySummaries));
                break;
        }

//        converter.serialize(productsCollection, RESOURCE_FILE_PATH + PRODUCTS_IN_RANGE_FILE_NAME + formatInput.toLowerCase());


//        String jsonString = gson
//                .toJson(this.productService.getProductsInRange(), ProductInRangeDto[].class);

//        Files.writeString(Path.of(RESOURCE_FILE_PATH + PRODUCTS_IN_RANGE_FILE), jsonString);
    }


    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
