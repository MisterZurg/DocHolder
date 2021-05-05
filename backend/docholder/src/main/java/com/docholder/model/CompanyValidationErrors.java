package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "company_validation_errors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CompanyValidationErrors {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "message")
    private String message;
}
