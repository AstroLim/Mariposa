package com.gabriel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "checkout_orders")
public class CheckoutOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "checkoutID")
    private Integer checkoutID;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "checkoutedItems", columnDefinition = "TEXT")
    private String checkoutedItems; // JSON array of items
    
    @Column(name = "status", nullable = false)
    private String status; // "pending", "received", "not_received", "cancelled"
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "created")
    private Date created;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "lastUpdated")
    private Date lastUpdated;
}

