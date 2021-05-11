package com.docholder.service;

import com.docholder.model.Document;
import com.docholder.model.DocumentRequest;
import com.docholder.model.NoticeStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    boolean putDocument(Document document, MultipartFile file);
    byte[] getDocument(UUID id);
    boolean deleteDocument(UUID id);
    Document getOneDocumentInfo(UUID id);
    List<Document> getDocumentsByCompany(UUID id);
    boolean request(DocumentRequest documentRequest);
    List<DocumentRequest> getRequestsByCompany(UUID companyId);
    List<DocumentRequest> getRequestsByUser(UUID userId);
    DocumentRequest getRequestByUserAndDocument(UUID userId, UUID documentId);
    boolean setRequestStatus(UUID id, NoticeStatus status);
}
