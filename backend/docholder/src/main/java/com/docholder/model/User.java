package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Column(name = "company_id")
    private UUID company_id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}