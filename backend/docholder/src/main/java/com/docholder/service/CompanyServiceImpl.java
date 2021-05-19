package com.docholder.service;

import com.docholder.model.*;
import com.docholder.repository.CompanyRepository;
import com.docholder.repository.JobOfferRepository;
import com.docholder.repository.UserRepository;
import com.docholder.utilities.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Jwt jwt;

    @Override
    public void create(Company company) {
        companyRepository.save(company);
    }

//    @Override
//    public long count(){
//        return companyRepository.count();
//    }
//
//    @Override
//    public Page<Company> findAllByPage(int limit, int page){
//        Pageable pageable = PageRequest.of(page, limit);
//        return companyRepository.findAll(pageable);
//    }

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
    public long countPublishedByNameAndStatus(String name, CompanyStatus status){
        return companyRepository.countAllByStatusAndNameContains(status, name);
    }
    @Override
    public Page<Company> findAllPublishedByPageAndNameAndStatus(int limit, int page, String searchName, CompanyStatus status){
        Pageable pageable = PageRequest.of(page, limit);
        return companyRepository.findAllByStatusAndNameContainingIgnoreCase(status, searchName, pageable);
    }

    @Override
    public long countPublishedByName(String name){
        return companyRepository.countAllByNameContains(name);
    }
    @Override
    public Page<Company> findAllPublishedByPageAndName(int limit, int page, String searchName){
        Pageable pageable = PageRequest.of(page, limit);
        return companyRepository.findAllByNameContainingIgnoreCase(searchName, pageable);
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

    @Override
    public boolean invite(JobOffer jobOffer, String email){

        try {
            jobOffer.setUserId( userRepository.findUserByEmail(email).getId() );
            jobOfferRepository.save(jobOffer);
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<JobOffer> getInvitations(UUID userId){
//        return jobOfferRepository.findAllByUser_id(userId);
        return jobOfferRepository.findAllByUser_id(userId);
    }

    @Override
    public String setInviteStatus(UUID id, NoticeStatus status){
        String newToken = null;
        try{
            JobOffer offer = jobOfferRepository.getOne(id);
            offer.setStatus(status);
            jobOfferRepository.save(offer);

            if (status == NoticeStatus.ACCEPTED){
                User user = userRepository.getOne(offer.getUserId());
                user.setCompany_id( offer.getCompanyId() );
                user.setRole( offer.getRole() );
                userRepository.save(user);

                newToken = jwt.generateTokenByUser( userMapper.entityToDto(user) );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return newToken;
    }

}
