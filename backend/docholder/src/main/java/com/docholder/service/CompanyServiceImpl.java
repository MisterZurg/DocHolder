package com.docholder.service;

import com.docholder.model.Company;
import com.docholder.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Override
    public void create(Company company) {
        companyRepository.save(company);
    }

    @Override
    public long count(){
        return companyRepository.count();
    }

    @Override
    public List<Company> readAll() {

        return companyRepository.findAll();
    }

    @Override
    public Company read(UUID id) {
        try {
            return  companyRepository.findById(id).get();
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public Page<Company> findAllByPage(Pageable pageable){
        return companyRepository.findAll(pageable);
    }

    @Override
    public boolean update(Company company, UUID id) {
        if (companyRepository.existsById(id)){
            company.setId(id);
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateLogo(UUID id, MultipartFile logo){
        // System.out.println(logo);
        Company company = companyRepository.getOne(id);


        try {
            company.setLogo(logo.getBytes());
            companyRepository.save(company);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
