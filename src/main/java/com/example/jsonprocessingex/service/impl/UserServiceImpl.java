package com.example.jsonprocessingex.service.impl;

import com.example.jsonprocessingex.model.dto.ProductBuyerDto;
import com.example.jsonprocessingex.model.dto.UserSeedDto;
import com.example.jsonprocessingex.model.dto.UserSoldProductsDto;
import com.example.jsonprocessingex.model.dto.problem4.ProductDto;
import com.example.jsonprocessingex.model.dto.problem4.ProductsCountDto;
import com.example.jsonprocessingex.model.dto.problem4.UserDto;
import com.example.jsonprocessingex.model.dto.problem4.UsersCountDto;
import com.example.jsonprocessingex.model.entity.Product;
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
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<UserSoldProductsDto> getAllUsersWithSoldProduct() {
        List<User> allUsersWithSoldProduct = this.userRepository.getAllUsersWithSoldProduct();
        return allUsersWithSoldProduct
                .stream().map(u -> modelMapper.map(u, UserSoldProductsDto.class))
                .map(u -> {
                    Set<ProductBuyerDto> soldProducts = u.getSoldProducts();
                    soldProducts.removeIf(p -> p.getBuyerLastName() == null);
                    return u;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UsersCountDto getAllUsersWithSoldProductOrderBySoldProducts() {
        List<User> users = this.userRepository.getAllUsersWithSoldProductOrderBySoldProducts();

        UsersCountDto dto = new UsersCountDto();
        dto.setCount(users.size());
        dto.setUsers(getUserDtos(users));

        return dto;
    }

    private List<UserDto> getUserDtos(List<User> users) {
        List<UserDto> result = new ArrayList<>();

        for (User user : users) {
            UserDto dto = modelMapper.map(user, UserDto.class);
            ProductsCountDto productsCountDto = new ProductsCountDto();
            productsCountDto.setCount(user.getSoldProducts().size());
            productsCountDto.setProducts(getProductDtos(user.getSoldProducts()));
            dto.setSoldProducts(productsCountDto);
            result.add(dto);
        }
        return result;
    }

    private Set<ProductDto> getProductDtos(Set<Product> soldProducts) {
        Set<ProductDto> result = new HashSet<>();
        for (Product soldProduct : soldProducts) {
            result.add(modelMapper.map(soldProduct, ProductDto.class));
        }
        return result;
    }
}
