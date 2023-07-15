package com.example.jsonprocessingex.model.dto.problem4;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDto {
    @Expose
    @XmlAttribute(name = "first-name")
    private String firstName;
    @Expose
    @XmlAttribute(name = "last-name")
    private String lastName;
    @Expose
    @XmlAttribute
    private Integer age;
    @Expose
    @XmlElement(name = "sold-products")
    private ProductsCountDto soldProducts;

    public UserDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductsCountDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductsCountDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
