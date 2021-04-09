package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserDto {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private UserGender gender;
    private UUID company_id;
    private UserRole role;
}