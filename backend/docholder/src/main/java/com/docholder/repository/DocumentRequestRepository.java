package com.docholder.repository;

import com.docholder.model.DocumentRequest;
import com.docholder.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, UUID> {

    @Query("SELECT r FROM DocumentRequest r WHERE (r.company_id = :companyId)")
    public List<DocumentRequest> findAllByCompany_id(UUID companyId);

    @Query("SELECT r FROM DocumentRequest r WHERE (r.user_id = :userId)")
    public List<DocumentRequest> findAllByUser_id(UUID userId);

    @Query("SELECT r FROM DocumentRequest r WHERE (r.user_id = :userId and r.document_id = :documentId)")
    public DocumentRequest getOneByUser_idAndDocument_id(UUID userId, UUID documentId);

    @Query("SELECT r FROM DocumentRequest r WHERE (r.user_id = :userId AND r.company_id = :companyId AND r.document_id = :documentId)")
    public DocumentRequest findByAllInfo(UUID userId, UUID companyId, UUID documentId);

}
