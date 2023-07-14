package com.example.jsonprocessingex.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductInRangeDto {
    @Expose
    @XmlAttribute(name = "name")
    private String name;
    @Expose
    @XmlAttribute(name = "price")
    private BigDecimal price;
    @Expose
    @SerializedName("seller")
    @XmlAttribute(name = "seller")
    private String sellerFullName;

    public ProductInRangeDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSellerFullName() {
        return sellerFullName;
    }

    public void setSellerFullName(String sellerFullName) {
        this.sellerFullName = sellerFullName;
    }
}
