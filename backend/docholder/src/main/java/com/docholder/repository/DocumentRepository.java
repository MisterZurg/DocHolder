package com.docholder.repository;

import com.docholder.model.Document;
import com.docholder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

    @Query("SELECT d FROM Document d where (d.company_id = :company_id)")
    List<Document> findDocumentsByCompany(@Param("company_id") String company_id);

}
