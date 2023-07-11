package com.example.jsonprocessingex.service;

import com.example.jsonprocessingex.model.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();

}
