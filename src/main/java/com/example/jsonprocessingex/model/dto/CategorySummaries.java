package com.example.jsonprocessingex.model.dto;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategorySummaries {
    @XmlElement(name = "category")
    @Expose
    private List<CategorySummaryDto> categoriesSummary;

    public CategorySummaries(List<CategorySummaryDto> categoriesSummary) {
        this.categoriesSummary = categoriesSummary;
    }

    public CategorySummaries() {
    }

    public List<CategorySummaryDto> getCategoriesSummary() {
        return categoriesSummary;
    }
}
