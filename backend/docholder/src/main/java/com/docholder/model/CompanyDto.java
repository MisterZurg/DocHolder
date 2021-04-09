package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CompanyDto {
    private UUID id;
    private String name;
    private String description;
    private byte[] logo;
    private CompanyStatus status;
}
