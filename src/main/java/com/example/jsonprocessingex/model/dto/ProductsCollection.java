package com.example.jsonprocessingex.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsCollection {
    @XmlElement(name = "product")
    private ProductInRangeDto[] products;

    public ProductsCollection() {
    }


    public ProductsCollection(ProductInRangeDto[] products) {
        this.products = products;
    }

    public ProductInRangeDto[] getProducts() {
        return products;
    }

    public void setProducts(ProductInRangeDto[] products) {
        this.products = products;
    }
}
