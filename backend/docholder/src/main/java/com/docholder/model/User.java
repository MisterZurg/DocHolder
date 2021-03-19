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
    @Column(name = "uid")
    @SequenceGenerator(name = "usersUIdSeq", sequenceName = "users_uid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersIdSeq")
    private UUID uid;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "companyID")
    private Integer companyID;

    private UserRole role;
    // TODO : private ... profilePicture;
    private UserGender gender;

    /*
     public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
    */
}
