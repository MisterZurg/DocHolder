package com.docholder.service;

import com.docholder.model.Company;
import com.docholder.model.CompanyStatus;
import com.docholder.model.CompanyValidationErrors;
import com.docholder.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Page<Company> findAllByPage(int limit, int page){
        Pageable pageable = PageRequest.of(page, limit);
        return companyRepository.findAll(pageable);
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
    public long countPublished(String name){
        return companyRepository.countAllByStatus(name, CompanyStatus.PUBLISHED);
    }

    @Override
    public Page<Company> findAllPublishedByPage(int limit, int page){
        Pageable pageable = PageRequest.of(page, limit);
        return companyRepository.findAllByStatus(CompanyStatus.PUBLISHED, pageable);
    }

    @Override
    public Page<Company> findPublishedCompanyByGivenName(int limit, int page, String name){
        Pageable pageable = PageRequest.of(page, limit);
        return companyRepository.findCompanyByGivenSearch(name,pageable);
    }


    @Override
    public boolean update(Company company) {
        try {
            if(company.getLogo() == null){
                company.setLogo( companyRepository.getOne(company.getId()).getLogo() );
            }
            companyRepository.save(company);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateLogo(UUID id, MultipartFile logo){
        Company company = companyRepository.getOne(id);

        try {
            company.setLogo(logo.getBytes());
            companyRepository.save(company);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateStatus(UUID id, CompanyStatus status, String message){
        try{
            Company company = companyRepository.getOne(id);
            company.setStatus(status);

            if(message.equals("null")){
                company.setErrorId(null);
            } else {
                CompanyValidationErrors companyError = new CompanyValidationErrors();
                companyError.setMessage(message);
                company.setErrorId(companyError);
            }

            companyRepository.save(company);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Catch error during updateStatus()");
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
