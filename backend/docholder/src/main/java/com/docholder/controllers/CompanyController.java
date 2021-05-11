package com.docholder.controllers;

import com.docholder.model.*;
import com.docholder.repository.CompanyRepository;
import com.docholder.repository.UserRepository;
import com.docholder.service.CompanyService;
import com.docholder.service.UserService;
import com.docholder.utilities.Jwt;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final Jwt jwt;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JobOfferMapper jobOfferMapper;

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasPermission(#token, 'createCompany')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CompanyDto companyDto, @RequestParam String token) {
        Company company = companyMapper.dtoToEntity(companyDto);
//            System.out.println(company);
//            System.out.println(token);

        // Will move to CustomPermissionEvaluator
        Map<String, Object> userdata = jwt.getData(token);
        User user = userService.read( UUID.fromString(userdata.get("id").toString()));
        if(!(user.getCompany_id() == null)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        company.setStatus(CompanyStatus.DRAFT);
        companyService.create(company);

        user.setCompany_id(company.getId());
        user.setRole(UserRole.DIRECTOR);
        userService.update(user, user.getId());

        String newtoken = jwt.generateTokenByUser( userMapper.entityToDto(user) );

        return new ResponseEntity<>(newtoken, HttpStatus.CREATED);
//        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value ="/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity(companyService.count(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Company>> readAllByPage(
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "page") int page)
    {
        Page<Company> companies = companyService.findAllByPage(limit, page);

        Page<CompanyDto> companiesDto = companyMapper.entityToDto(companies);
        return companiesDto != null &&  !companiesDto.isEmpty()
                ? new ResponseEntity(companiesDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value ="/read")
    public ResponseEntity<List<Company>> read(@RequestParam(name = "id") UUID id) {
        final Company company = companyService.read(id);

        return company != null
                ? new ResponseEntity(companyMapper.entityToDto(company), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value ="/countPublished")
    public ResponseEntity<?> countPublished(@RequestParam(name = "filter") String name) {
        return new ResponseEntity(companyService.countPublishedByName(name), HttpStatus.OK);
    }

    @GetMapping(value = "published")
    public ResponseEntity<List<Company>> readAllPublishedByPage(
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "filter") String name)
    {
        Page<Company> companies = companyService.findAllPublishedByPageAndName(limit, page, name);

        Page<CompanyDto> companiesDto = companyMapper.entityToDto(companies);
        return companiesDto != null &&  !companiesDto.isEmpty()
                ? new ResponseEntity(companiesDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasPermission(#token, 'updateCompany')")
    @PostMapping(value = "/logo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateLogo(
            @RequestParam("id") UUID id,
            @RequestParam String token,
            @RequestPart("file") MultipartFile logo)
    {
//        companyService.updateLogo(id,logo);
        return companyService.updateLogo(id, logo)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasPermission(#token, 'updateCompany')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody CompanyDto companyDto, @RequestParam String token) {
        Company company = companyMapper.dtoToEntity(companyDto);
        company.setStatus(CompanyStatus.READY_TO_VERIFY);

        final boolean updated = companyService.update(company);

        return updated
                ? new ResponseEntity<>(companyMapper.entityToDto(company), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasPermission(#token, 'updateCompanyStatus')")
    @PutMapping(value = "updateStatus")
    public ResponseEntity<?> updateStatus(
            @RequestParam UUID id,
            @RequestParam CompanyStatus status,
            @RequestParam String message,
            @RequestParam String token)
    {
        boolean is_updated = companyService.updateStatus(id, status, message);

        return is_updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
//        final boolean deleted = companyService.delete(id);
//
//        return deleted
//                ? new ResponseEntity<>(HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }

    @PreAuthorize("hasPermission(#token, 'updateCompany')")
    @PostMapping(value = "/invite")
    public ResponseEntity<?> invite(@RequestBody JobOffer jobOffer, @RequestParam String email, @RequestParam String token){

        System.out.println(jobOffer);
        System.out.println(email);

        return companyService.invite(jobOffer, email)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @GetMapping(value = "/invite")
    public ResponseEntity<?> getInvitations(@RequestParam(name = "user_id") UUID userId){
        List<JobOffer> jobOffer = companyService.getInvitations(userId);
        List<JobOfferDto> jobOffersDto = jobOfferMapper.entityToDto(jobOffer);
        jobOffersDto.stream().forEach(jobOfferDto -> {
            jobOfferDto.setCompany_name( companyRepository.getOne(jobOfferDto.getCompany_id()).getName() );
            jobOfferDto.setEmployer_name( userRepository.getOne(jobOfferDto.getEmployer_id()).getName() );
            jobOfferDto.setEmployer_surname( userRepository.getOne(jobOfferDto.getEmployer_id()).getSurname() );
        });
        return new ResponseEntity<>(jobOffersDto, HttpStatus.OK);
    }
    @PreAuthorize("hasPermission(new com.docholder.utilities.JobOfferSecurityTransfer(#token, #id), 'setJobOfferStatus')")
    @PostMapping(value = "/invite/status")
    public ResponseEntity<?> setInviteStatus(
            @RequestParam UUID id,
            @RequestParam NoticeStatus status,
            @RequestParam String token)
    {
        String newToken = companyService.setInviteStatus(id, status);
        return new ResponseEntity<>(newToken, HttpStatus.OK);
    }
}