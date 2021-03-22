package com.docholder.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Company {
    private UUID id;
    private String name;
    private String description;
    // TODO : private ... logo;
    private boolean companyStatus;

}
