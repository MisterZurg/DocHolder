package com.docholder.service;

import com.docholder.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    void create(Company company);

    long count();

    List<Company> readAll();

    Company read(UUID id);

    Page<Company> findAllByPage(Pageable pageable);

//    User readByEmail(String email);

    boolean update(Company company, UUID id);

    // boolean updateLogo(UUID id, MultipartFile logo);
    boolean updateLogo(UUID id, MultipartFile logo);

    boolean delete(UUID id);

}
