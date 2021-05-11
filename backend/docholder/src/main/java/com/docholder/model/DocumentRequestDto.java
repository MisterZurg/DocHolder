package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentRequestDto {

    private UUID id;
    private UUID user_id;
    private UUID company_id;
    private UUID document_id;
    private String message;
    private NoticeStatus status;

    private String company_name;
    private String user_name;
    private String document_name;
}
