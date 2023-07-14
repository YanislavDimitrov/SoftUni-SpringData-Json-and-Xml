package com.example.jsonprocessingex.model.dto;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CategorySummaryDto {
    @Expose
    @XmlAttribute(name = "name")
    private String name;
    @Expose
    @XmlElement(name = "product_count")
    private int productsCount;
    @Expose
    @XmlElement(name = "avg_price")
    private BigDecimal avgPrice;
    @Expose
    @XmlElement(name = "total_price")
    private BigDecimal totalPrice;

    public CategorySummaryDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(int productsCount) {
        this.productsCount = productsCount;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
