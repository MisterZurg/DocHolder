package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.mapstruct.Mapping;

import java.util.UUID;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentDto {
    private UUID id;
    private String name;
    private String filename;
    private String company_id;
    private String user_id;
    private UserRole role_modify;
    private UserRole role_read;
}
