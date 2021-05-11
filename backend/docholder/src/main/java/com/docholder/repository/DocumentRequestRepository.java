package com.docholder.repository;

import com.docholder.model.DocumentRequest;
import com.docholder.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, UUID> {

    @Query("SELECT r FROM DocumentRequest r WHERE (r.companyId = :companyId)")
    public List<DocumentRequest> findAllByCompany_id(UUID companyId);

    @Query("SELECT r FROM DocumentRequest r WHERE (r.userId = :userId)")
    public List<DocumentRequest> findAllByUser_id(UUID userId);

    @Query("SELECT r FROM DocumentRequest r WHERE (r.userId = :userId and r.documentId = :documentId)")
    public DocumentRequest getOneByUser_idAndDocument_id(UUID userId, UUID documentId);

    @Query("SELECT r FROM DocumentRequest r WHERE (r.userId = :userId AND r.companyId = :companyId AND r.documentId = :documentId)")
    public DocumentRequest findByAllInfo(UUID userId, UUID companyId, UUID documentId);

}
