package com.docholder.model;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-11T22:24:56+0300",
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
        documentRequest.setUserId( documentRequestDto.getUserId() );
        documentRequest.setCompanyId( documentRequestDto.getCompanyId() );
        documentRequest.setDocumentId( documentRequestDto.getDocumentId() );
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
        documentRequestDto.setUserId( documentRequest.getUserId() );
        documentRequestDto.setCompanyId( documentRequest.getCompanyId() );
        documentRequestDto.setDocumentId( documentRequest.getDocumentId() );
        documentRequestDto.setMessage( documentRequest.getMessage() );
        documentRequestDto.setStatus( documentRequest.getStatus() );

        return documentRequestDto;
    }
}
