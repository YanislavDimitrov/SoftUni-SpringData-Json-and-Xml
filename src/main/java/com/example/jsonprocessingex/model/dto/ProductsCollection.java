package com.example.jsonprocessingex.model.dto;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsCollection {
    @XmlElement(name = "product")
    @Expose
    private List<ProductInRangeDto> products;

    public ProductsCollection() {
    }

    public ProductsCollection(List<ProductInRangeDto> products) {
        this.products = products;
    }

    public List<ProductInRangeDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInRangeDto> products) {
        this.products = products;
    }
}
