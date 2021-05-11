package com.docholder.model;

import com.docholder.repository.CompanyRepository;
import com.docholder.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
//@RequiredArgsConstructor
public abstract class JobOfferMapper {
//    private final CompanyRepository companyRepository;
//    private final UserRepository userRepository;
//
//
//    JobOfferDto entityToDto(JobOffer jobOffer){
//        JobOfferDto jobOfferDto = entityToDtoHelper(jobOffer);
//        jobOfferDto.setCompany_name( companyRepository.getOne(jobOfferDto.getCompany_id()).getName() );
//        jobOfferDto.setEmployer_name( userRepository.getOne(jobOfferDto.getEmployer_id()).getName() );
//        jobOfferDto.setEmployer_surname( userRepository.getOne(jobOfferDto.getEmployer_id()).getSurname() );
//        return jobOfferDto;
//    }

    public abstract JobOffer dtoToEntity(JobOfferDto jobOfferDto);
    public abstract JobOfferDto entityToDto(JobOffer jobOffer);
//    public abstract JobOfferDto entityToDtoHelper(JobOffer jobOffer);

    public List<JobOfferDto> entityToDto(List<JobOffer> jobOffers){
        return jobOffers.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
