package com.gabriel.model;

import lombok.Data;

@Data
public class ProductVariation {
    private Integer id;
    private String quantity;
    private Double price;
    
    public ProductVariation() {}
    
    public ProductVariation(Integer id, String quantity, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }
}
