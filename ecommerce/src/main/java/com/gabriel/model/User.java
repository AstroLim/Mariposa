package com.gabriel.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
}

