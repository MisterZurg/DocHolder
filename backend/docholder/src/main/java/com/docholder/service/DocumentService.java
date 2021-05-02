package com.docholder.service;

import com.docholder.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    boolean putDocument(Document document, MultipartFile file);
    byte[] getDocument(UUID id);
    boolean deleteDocument(UUID id);
    Document getOneDocumentInfo(UUID id);
    List<Document> getDocumentsByCompany(UUID id);

}
