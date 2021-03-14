package com.docholder.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Integer companyID;
    private UserRole role;
    // TODO : private ... profilePicture;
    private UserGender gender;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
