package com.example.jsonprocessingex.service.impl;

import com.example.jsonprocessingex.model.dto.CategorySeedDto;
import com.example.jsonprocessingex.model.entity.Category;
import com.example.jsonprocessingex.repository.CategoryRepository;
import com.example.jsonprocessingex.service.CategoryService;
import com.example.jsonprocessingex.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.example.jsonprocessingex.constants.GlobalConstant.RESOURCE_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORIES_FILE_NAME = "Categories.json";
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() > 0) {
            return;
        }
        String jsonContent = Files.readString(Path.of(RESOURCE_FILE_PATH + CATEGORIES_FILE_NAME));
        Arrays.stream(gson.fromJson(jsonContent, CategorySeedDto[].class))
                .filter(validationUtil::isValid)
                .map(c -> modelMapper.map(c, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        int categoriesCountToAdd = random.nextInt(1, 4);
        long categoriesCount = categoryRepository.count() + 1;

        for (int i = 0; i < categoriesCountToAdd; i++) {
            long randomId = random.nextLong(1, categoriesCount);

            categories.add(categoryRepository.findById(randomId).orElse(null));
        }
        return categories;
    }
}
