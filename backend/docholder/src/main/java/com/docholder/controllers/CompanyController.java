package com.docholder.controllers;

import com.docholder.model.*;
import com.docholder.repository.CompanyRepository;
import com.docholder.repository.JobOfferRepository;
import com.docholder.repository.UserRepository;
import com.docholder.service.CompanyService;
import com.docholder.service.UserService;
import com.docholder.utilities.Jwt;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
//@Log4j2
//@CrossOrigin(exposedHeaders = "*")
public class CompanyController {

//    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final Jwt jwt;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JobOfferMapper jobOfferMapper;

    private final CompanyRepository companyRepository;
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasPermission(#token, 'createCompany')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CompanyDto companyDto, @RequestParam String token) {
        Company company = companyMapper.dtoToEntity(companyDto);

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
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<List<Company>> read(@PathVariable UUID id) {
        final Company company = companyService.read(id);

        return company != null
                ? new ResponseEntity(companyMapper.entityToDto(company), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Company>> readAllByPage(
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "status") @Nullable CompanyStatus status)
    {
        Page<Company> companies = null;

        if(status != null)
            companies = companyService.findAllPublishedByPageAndNameAndStatus(limit, page, name, status);
        else
            companies = companyService.findAllPublishedByPageAndName(limit, page, name);

        Page<CompanyDto> companiesDto = companyMapper.entityToDto(companies);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        header.setRange(Collections.singletonList(HttpRange.createByteRange(page, companies.getTotalPages())));

        return companiesDto != null &&  !companiesDto.isEmpty()
                ? new ResponseEntity(companiesDto, header, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasPermission(#token, 'updateCompany')")
    @PutMapping(value = "/{id}/logo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateLogo(
            @PathVariable UUID id,
            @RequestParam String token,
            @RequestPart("file") MultipartFile logo)
    {
        if(logo.getSize() > 5242880) return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);

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
    @PutMapping(value = "{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable UUID id,
            @RequestParam CompanyStatus status,
            @RequestParam String message,
            @RequestParam String token)
    {
        boolean is_updated = companyService.updateStatus(id, status, message);

        return is_updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}