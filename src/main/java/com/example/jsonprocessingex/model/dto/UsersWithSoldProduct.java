package com.example.jsonprocessingex.model.dto;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldProduct {
    @Expose
    @XmlElement(name = "user")
    List<UserSoldProductsDto> users;

    public UsersWithSoldProduct() {
    }


    public UsersWithSoldProduct(List<UserSoldProductsDto> users) {
        this.users = users;
    }

    public List<UserSoldProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserSoldProductsDto> users) {
        this.users = users;
    }
}
