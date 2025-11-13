package com.gabriel.model;

import lombok.Data;
import java.util.List;

@Data
public class ProductWithVariations {
    private String name;
    private String description;
    private String imageFile;
    private List<ProductVariation> variations;
    
    public ProductWithVariations() {}
    
    public ProductWithVariations(String name, String description, String imageFile, List<ProductVariation> variations) {
        this.name = name;
        this.description = description;
        this.imageFile = imageFile;
        this.variations = variations;
    }
}
