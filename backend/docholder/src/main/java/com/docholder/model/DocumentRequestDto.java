package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentRequestDto {

    private UUID id;
    private UUID userId;
    private UUID companyId;
    private UUID documentId;
    private String message;
    private NoticeStatus status;

    private String companyName;
    private String userFullName;
    private String documentName;
}
