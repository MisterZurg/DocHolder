package com.docholder.controllers;

import com.docholder.model.Company;
import com.docholder.model.CompanyDto;
import com.docholder.model.CompanyMapper;
import com.docholder.model.CompanyStatus;
import com.docholder.service.CompanyService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CompanyDto companyDto) {
        Company company = companyMapper.dtoToEntity(companyDto);
        company.setStatus(CompanyStatus.DRAFT);
        companyService.create(company);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value ="/{id}}")
    public ResponseEntity<List<Company>> read(@PathVariable(name = "id") UUID id) {
        final Company company = companyService.read(id);

        return company != null
                ? new ResponseEntity(companyMapper.entityToDto(company), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value ="/{limit}/{page}")
    public ResponseEntity<List<Company>> readAllByPage(
            @PathVariable(name = "limit") int limit,
            @PathVariable(name = "page") int page)
    {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Company> companies = companyService.findAllByPage(pageable);

//        How To
//        Page<CompanyDto> companiesDto;

        return companies != null &&  !companies.isEmpty()
                ? new ResponseEntity(companies, HttpStatus.OK)
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

    @PutMapping
    public ResponseEntity<?> update(@RequestBody CompanyDto companyDto) {
        Company company = companyMapper.dtoToEntity(companyDto);
        final boolean updated = companyService.update(company, company.getId());

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
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