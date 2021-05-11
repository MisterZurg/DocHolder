package com.docholder.model;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-09T20:04:57+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_282 (Amazon.com Inc.)"
)
@Component
public class DocumentRequestMapperImpl extends DocumentRequestMapper {

    @Override
    public DocumentRequest dtoToEntity(DocumentRequestDto documentRequestDto) {
        if ( documentRequestDto == null ) {
            return null;
        }

        DocumentRequest documentRequest = new DocumentRequest();

        documentRequest.setId( documentRequestDto.getId() );
        documentRequest.setUser_id( documentRequestDto.getUser_id() );
        documentRequest.setCompany_id( documentRequestDto.getCompany_id() );
        documentRequest.setDocument_id( documentRequestDto.getDocument_id() );
        documentRequest.setMessage( documentRequestDto.getMessage() );
        documentRequest.setStatus( documentRequestDto.getStatus() );

        return documentRequest;
    }

    @Override
    public DocumentRequestDto entityToDto(DocumentRequest documentRequest) {
        if ( documentRequest == null ) {
            return null;
        }

        DocumentRequestDto documentRequestDto = new DocumentRequestDto();

        documentRequestDto.setId( documentRequest.getId() );
        documentRequestDto.setUser_id( documentRequest.getUser_id() );
        documentRequestDto.setCompany_id( documentRequest.getCompany_id() );
        documentRequestDto.setDocument_id( documentRequest.getDocument_id() );
        documentRequestDto.setMessage( documentRequest.getMessage() );
        documentRequestDto.setStatus( documentRequest.getStatus() );

        return documentRequestDto;
    }
}
