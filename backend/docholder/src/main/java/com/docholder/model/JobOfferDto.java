package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JobOfferDto {
    private UUID id;
    private UUID userId;
    private UUID companyId;
    private UUID employerId;
    private UserRole role;
    private String message;
    private NoticeStatus status;

    private String companyName;
    private String employerFullName;
}
