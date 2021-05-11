package com.docholder.service;

import com.docholder.model.Company;
import com.docholder.model.CompanyStatus;
import com.docholder.model.JobOffer;
import com.docholder.model.NoticeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    void create(Company company);

    long count();
    Page<Company> findAllByPage(int limit, int page);

    List<Company> readAll();

    Company read(UUID id);


    long countPublishedByName(String name);
    Page<Company> findAllPublishedByPageAndName(int limit, int page, String searchName);

//    User readByEmail(String email);

    boolean update(Company company);

    boolean updateLogo(UUID id, MultipartFile logo);

    boolean updateStatus(UUID id, CompanyStatus status, String message);

    boolean delete(UUID id);

    boolean invite(JobOffer jobOffer, String email);

    List<JobOffer> getInvitations(UUID userId);

    String setInviteStatus(UUID id, NoticeStatus status);
}
