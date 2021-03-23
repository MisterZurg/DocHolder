package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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

    @Column(name = "phone")
    private String phone;

    @Column(name = "company_id")
    private UUID company_id;
    /*
    @Column(name = "column_name")
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    // TODO : private ... profilePicture;
    @Column(name = "column_name")
    @Enumerated(value = EnumType.STRING)
    private UserGender gender;

    */
}
