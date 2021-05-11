package com.docholder.model;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-11T21:48:59+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_282 (Amazon.com Inc.)"
)
@Component
public class JobOfferMapperImpl extends JobOfferMapper {

    @Override
    public JobOffer dtoToEntity(JobOfferDto jobOfferDto) {
        if ( jobOfferDto == null ) {
            return null;
        }

        JobOffer jobOffer = new JobOffer();

        jobOffer.setId( jobOfferDto.getId() );
        jobOffer.setUserId( jobOfferDto.getUserId() );
        jobOffer.setCompanyId( jobOfferDto.getCompanyId() );
        jobOffer.setEmployerId( jobOfferDto.getEmployerId() );
        jobOffer.setRole( jobOfferDto.getRole() );
        jobOffer.setMessage( jobOfferDto.getMessage() );
        jobOffer.setStatus( jobOfferDto.getStatus() );

        return jobOffer;
    }

    @Override
    public JobOfferDto entityToDto(JobOffer jobOffer) {
        if ( jobOffer == null ) {
            return null;
        }

        JobOfferDto jobOfferDto = new JobOfferDto();

        jobOfferDto.setId( jobOffer.getId() );
        jobOfferDto.setUserId( jobOffer.getUserId() );
        jobOfferDto.setCompanyId( jobOffer.getCompanyId() );
        jobOfferDto.setEmployerId( jobOffer.getEmployerId() );
        jobOfferDto.setRole( jobOffer.getRole() );
        jobOfferDto.setMessage( jobOffer.getMessage() );
        jobOfferDto.setStatus( jobOffer.getStatus() );

        return jobOfferDto;
    }
}
