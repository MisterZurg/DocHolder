package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JobOfferDto {
    private UUID id;
    private UUID user_id;
    private UUID company_id;
    private UUID employer_id;
    private UserRole role;
    private String message;
    private NoticeStatus status;

    private String company_name;
    private String employer_name;
    private String employer_surname;
}
