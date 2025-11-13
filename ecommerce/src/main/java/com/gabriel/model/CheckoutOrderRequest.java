package com.gabriel.model;

import lombok.Data;
import java.util.List;

@Data
public class CheckoutOrderRequest {
    private String email;
    private List<String> checkoutedItems; // List of product names
    private String status; // "pending" by default
}

