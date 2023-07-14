package com.example.jsonprocessingex.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CategorySummaryDto {
    @Expose
    @SerializedName("category")
    @XmlAttribute
    private String name;
    @Expose
    @XmlElement(name = "product-count")
    private int productsCount;
    @Expose
    @XmlElement(name = "average-price")
    private BigDecimal averagePrice;
    @Expose
    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;

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

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
