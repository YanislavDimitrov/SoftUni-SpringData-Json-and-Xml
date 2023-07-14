package com.example.jsonprocessingex.service;

import com.example.jsonprocessingex.model.dto.CategorySummaryDto;
import com.example.jsonprocessingex.model.dto.UserSoldProductsDto;
import com.example.jsonprocessingex.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User getRandomUser();

    List<UserSoldProductsDto> getAllUsersWithSoldProduct();
}
