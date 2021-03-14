package com.docholder.model;

import lombok.Data;

@Data
public class Document {     // need 2 decide name File
    private Integer id;
    private String name;
    // TODO : private ... fileRole (documentRole)
    private Integer companyID;
    private Integer userID; // author
}
