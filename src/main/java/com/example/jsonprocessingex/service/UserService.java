package com.example.jsonprocessingex.service;

import com.example.jsonprocessingex.model.entity.User;

import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException;

    User getRandomUser();
}
