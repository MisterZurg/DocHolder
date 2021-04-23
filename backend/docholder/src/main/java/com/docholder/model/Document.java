package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "documents")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Document {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "filename")
    private String filename;

    @Column(name = "company_id")
    private String company_id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "role_modify")
    @Enumerated(EnumType.STRING)
    private UserRole role_modify;

    @Column(name = "role_read")
    @Enumerated(EnumType.STRING)
    private UserRole role_read;
}