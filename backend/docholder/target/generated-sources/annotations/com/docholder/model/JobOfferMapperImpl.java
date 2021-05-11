package com.docholder.model;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-09T13:13:43+0300",
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
        jobOffer.setUser_id( jobOfferDto.getUser_id() );
        jobOffer.setCompany_id( jobOfferDto.getCompany_id() );
        jobOffer.setEmployer_id( jobOfferDto.getEmployer_id() );
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
        jobOfferDto.setUser_id( jobOffer.getUser_id() );
        jobOfferDto.setCompany_id( jobOffer.getCompany_id() );
        jobOfferDto.setEmployer_id( jobOffer.getEmployer_id() );
        jobOfferDto.setRole( jobOffer.getRole() );
        jobOfferDto.setMessage( jobOffer.getMessage() );
        jobOfferDto.setStatus( jobOffer.getStatus() );

        return jobOfferDto;
    }
}
