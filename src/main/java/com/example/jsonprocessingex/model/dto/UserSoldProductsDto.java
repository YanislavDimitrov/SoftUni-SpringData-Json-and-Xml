package com.example.jsonprocessingex.model.dto;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.*;
import java.util.Set;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsDto {
    @Expose
    @XmlAttribute(name = "first-name")
    private String firstName;
    @Expose
    @XmlAttribute(name = "last-name")
    private String lastName;
    @Expose
    @XmlElement(name = "product")
    @XmlElementWrapper(name = "sold-products")
    private Set<ProductBuyerDto> soldProducts;

    public UserSoldProductsDto() {
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

    public Set<ProductBuyerDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<ProductBuyerDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
