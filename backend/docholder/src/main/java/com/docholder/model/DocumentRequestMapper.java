package com.docholder.model;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public abstract class DocumentRequestMapper {

    public abstract DocumentRequest dtoToEntity(DocumentRequestDto documentRequestDto);
    public abstract DocumentRequestDto entityToDto(DocumentRequest documentRequest);

    public List<DocumentRequestDto> entityToDto(List<DocumentRequest> documentRequests){
        return documentRequests.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
