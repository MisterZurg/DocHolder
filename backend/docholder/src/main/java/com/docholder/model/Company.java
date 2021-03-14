package com.docholder.model;

import lombok.Data;

@Data
public class Company {
    private Integer id;
    private String name;
    private String description;
    // TODO : private ... logo;
    private boolean companyStatus;

}
