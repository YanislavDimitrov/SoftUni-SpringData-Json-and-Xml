package com.example.jsonprocessingex.model.dto.problem4;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.*;
import java.util.Set;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsCountDto {
    @Expose
    @XmlAttribute
    private Integer count;
    @Expose
    @XmlElement(name = "product")
    private Set<ProductDto> products;

    public ProductsCountDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDto> products) {
        this.products = products;
    }
}
