package com.gabriel.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "riceproduct_data")
public class RiceProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "riceID")
    private int riceID;
    
    @Column(name = "riceName")
    private String riceName;
    
    @Column(name = "riceDescription")
    private String riceDescription;
    
    @Column(name = "riceQuantity")
    private String riceQuantity;
    
    @Column(name = "ricePrice", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal ricePrice;
    
    @Column(name = "riceImageFile")
    private String riceImageFile;
}
