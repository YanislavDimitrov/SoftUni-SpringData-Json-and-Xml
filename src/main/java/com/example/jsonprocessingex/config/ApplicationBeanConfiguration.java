package com.example.jsonprocessingex.config;

import com.example.jsonprocessingex.model.dto.CategorySummaryDto;
import com.example.jsonprocessingex.model.dto.ProductInRangeDto;
import com.example.jsonprocessingex.model.entity.Category;
import com.example.jsonprocessingex.model.entity.Product;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public GsonBuilder gsonBuilder() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Product, String> converter = mappingContext -> String.format("%s %s"
                , mappingContext.getSource().getSeller().getFirstName() == null
                        ? "Mr./Mrs."
                        : mappingContext.getSource().getSeller().getFirstName()
                , mappingContext.getSource().getSeller().getLastName());

        TypeMap<Product, ProductInRangeDto> productProductInRangeDtoTypeMap = modelMapper.typeMap(Product.class, ProductInRangeDto.class);
        productProductInRangeDtoTypeMap.addMappings(m -> m.using(converter).map(src -> src, (dest, value) -> dest.setSellerFullName((String) value)));


        Converter<Set<Product>, Integer> productsToCountConverter = new Converter<Set<Product>, Integer>() {
            @Override
            public Integer convert(MappingContext<Set<Product>, Integer> mappingContext) {
                //TODO try to remove if statement
                if (mappingContext.getSource() != null) {
                    return mappingContext.getSource().size();
                }
                return 0;
            }
        };
        Converter<Set<Product>, BigDecimal> avgPriceConverter = new Converter<Set<Product>, BigDecimal>() {
            @Override
            public BigDecimal convert(MappingContext<Set<Product>, BigDecimal> mappingContext) {
                return getAvgProductsPrice(mappingContext.getSource());
            }
        };
        Converter<Set<Product>, BigDecimal> totalPriceConverter = new Converter<Set<Product>, BigDecimal>() {
            @Override
            public BigDecimal convert(MappingContext<Set<Product>, BigDecimal> mappingContext) {
                return getTotalProductsPrice(mappingContext.getSource());
            }
        };

        TypeMap<Category, CategorySummaryDto> categorySummaryDtoTypeMap = modelMapper.typeMap(Category.class, CategorySummaryDto.class);

        categorySummaryDtoTypeMap.addMappings(m -> m.using(productsToCountConverter).map(Category::getProducts, CategorySummaryDto::setProductsCount));
        categorySummaryDtoTypeMap.addMappings(m -> m.using(avgPriceConverter).map(Category::getProducts, CategorySummaryDto::setAvgPrice));
        categorySummaryDtoTypeMap.addMappings(m -> m.using(totalPriceConverter).map(Category::getProducts, CategorySummaryDto::setTotalPrice));
        return modelMapper;
    }

    private BigDecimal getTotalProductsPrice(Set<Product> products) {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : products) {
            total = total.add(product.getPrice());
        }
        return total;
    }

    private BigDecimal getAvgProductsPrice(Set<Product> products) {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : products) {
            total = total.add(product.getPrice());
        }
        return total.divide(BigDecimal.valueOf(products.size()), RoundingMode.HALF_EVEN);
    }
}
