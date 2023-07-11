package com.example.jsonprocessingex.service.impl;

import com.example.jsonprocessingex.model.dto.UserSeedDto;
import com.example.jsonprocessingex.model.entity.User;
import com.example.jsonprocessingex.repository.UserRepository;
import com.example.jsonprocessingex.service.UserService;
import com.example.jsonprocessingex.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

import static com.example.jsonprocessingex.constants.GlobalConstant.RESOURCE_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {
    public static final String USERS_FILE_NAME = "Users.json";
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public UserServiceImpl(Gson gson, ModelMapper modelMapper, UserRepository userRepository, ValidationUtil validationUtil) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() > 0) {
            return;
        }
        String jsonContent = Files.readString(Path.of(RESOURCE_FILE_PATH + USERS_FILE_NAME));

        Arrays.stream(gson.fromJson(jsonContent, UserSeedDto[].class))
                .filter(u -> validationUtil.isValid(u))
                .map(u -> modelMapper.map(u, User.class))
                .forEach(userRepository::save);

    }

    @Override
    public User getRandomUser() {
        long randomUserId = new Random().nextLong(1, userRepository.count() + 1);
        return userRepository.findById(randomUserId).orElse(null);
    }
}
