package com.docholder.model;

import java.util.Arrays;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-13T13:41:59+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_282 (Amazon.com Inc.)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyDto entityToDto(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyDto companyDto = new CompanyDto();

        companyDto.setId( company.getId() );
        companyDto.setName( company.getName() );
        companyDto.setDescription( company.getDescription() );
        byte[] logo = company.getLogo();
        if ( logo != null ) {
            companyDto.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        companyDto.setStatus( company.getStatus() );

        return companyDto;
    }

    @Override
    public Company dtoToEntity(CompanyDto companyDto) {
        if ( companyDto == null ) {
            return null;
        }

        Company company = new Company();

        company.setId( companyDto.getId() );
        company.setName( companyDto.getName() );
        company.setDescription( companyDto.getDescription() );
        byte[] logo = companyDto.getLogo();
        if ( logo != null ) {
            company.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        company.setStatus( companyDto.getStatus() );

        return company;
    }
}
