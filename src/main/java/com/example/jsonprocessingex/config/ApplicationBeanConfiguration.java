package com.example.jsonprocessingex.config;

import com.example.jsonprocessingex.model.dto.ProductInRangeDto;
import com.example.jsonprocessingex.model.entity.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
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
        return modelMapper;
    }
}
