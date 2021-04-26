package com.docholder.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DocumentMapper {

    DocumentDto entityToDto(Document document);

//    @Mapping(target = "filename", ignore = true)
    Document dtoToEntity(DocumentDto documentDto);

    default List<DocumentDto> entityToDto(List<Document> documents){
        return documents.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
