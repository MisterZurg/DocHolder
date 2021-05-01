package com.docholder.controllers;

import com.docholder.model.*;
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

    @PreAuthorize("hasPermission(#token, 'createCompany')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CompanyDto companyDto, @RequestParam String token) {
        Company company = companyMapper.dtoToEntity(companyDto);
//            System.out.println(company);
//            System.out.println(token);

        // Will move to user service
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


        return new ResponseEntity<>(HttpStatus.CREATED);
//        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value ="/count")
    public ResponseEntity<List<Company>> count() {
        return new ResponseEntity(companyService.count(), HttpStatus.OK);
    }

    @GetMapping(value ="/read")
    public ResponseEntity<List<Company>> read(@RequestParam(name = "id") UUID id) {
        final Company company = companyService.read(id);

        return company != null
                ? new ResponseEntity(companyMapper.entityToDto(company), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Company>> readAllByPage(
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "page") int page)
    {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Company> companies = companyService.findAllByPage(pageable);

        Page<CompanyDto> companiesDto = companyMapper.entityToDto(companies);
        return companiesDto != null &&  !companiesDto.isEmpty()
                ? new ResponseEntity(companiesDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping(value = "/logo", consumes = { MediaType.ALL_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    @PostMapping(value = "/logo")
    public ResponseEntity<?> updateLogo(
            @RequestPart UUID id,
            @RequestPart("file") MultipartFile logo
            )
    {
//        companyService.updateLogo(id, logo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#token, 'updateCompany')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody CompanyDto companyDto, @RequestParam String token) {
//        System.out.println(companyDto);
        Company company = companyMapper.dtoToEntity(companyDto);
        company.setStatus(CompanyStatus.READY_TO_VERIFY);
//        System.out.println(company);
        final boolean updated = companyService.update(company, company.getId());

//        boolean updated = true;
        return updated
                ? new ResponseEntity<>(companyMapper.entityToDto(company), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final boolean deleted = companyService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}