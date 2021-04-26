package com.docholder.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CompanyMapper {

    CompanyDto entityToDto(Company company);

    Company dtoToEntity(CompanyDto companyDto);

    default Page<CompanyDto> entityToDto(Page<Company> companies){
        return new PageImpl<CompanyDto>(companies.getContent().stream().map(this::entityToDto).collect(Collectors.toList()));
    }
}
//    Page<Company> pageDtoToEntity(Page<CompanyDto> companiesDto);
//    Page<CompanyDto> pageEntityToDto(Page<Company> companies);
