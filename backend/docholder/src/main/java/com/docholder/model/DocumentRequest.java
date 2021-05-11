package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "document_requests")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentRequest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID user_id;

    private UUID company_id;

    private UUID document_id;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NoticeStatus status = NoticeStatus.NOT_ANSWERED;

}
