package com.example.jsonprocessingex;

import com.example.jsonprocessingex.model.dto.ProductsCollection;
import com.example.jsonprocessingex.service.CategoryService;
import com.example.jsonprocessingex.service.ProductService;
import com.example.jsonprocessingex.service.UserService;
import com.example.jsonprocessingex.util.FormatConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.jsonprocessingex.constants.GlobalConstant.RESOURCE_FILE_PATH;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    public static final String PRODUCTS_IN_RANGE_JSON = "products-in-range.json";
    public static final String PRODUCTS_IN_RANGE_XML = "products-in-range.xml";
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final FormatConverter formatConverter;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService,
                                 UserService userService,
                                 ProductService productService,
                                 @Qualifier("xml_format_converter") FormatConverter formatConverter) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.formatConverter = formatConverter;
    }


    @Override
    public void run(String... args) throws Exception {
        formatConverter.setPrettyPrinting();
        seedData();
        ProductsCollection productsCollection = new ProductsCollection(this.productService.getProductsInRange());
        formatConverter.serialize(productsCollection, RESOURCE_FILE_PATH + PRODUCTS_IN_RANGE_XML);

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
