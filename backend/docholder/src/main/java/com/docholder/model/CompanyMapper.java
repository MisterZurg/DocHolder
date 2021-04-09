package com.docholder.model;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CompanyMapper {

    CompanyDto entityToDto(Company company);

    Company dtoToEntity(CompanyDto companyDto);

}
//    Page<Company> pageDtoToEntity(Page<CompanyDto> companiesDto);
//    Page<CompanyDto> pageEntityToDto(Page<Company> companies);
