package com.docholder.model;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< Updated upstream
    date = "2021-04-26T17:25:06+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.8 (Oracle Corporation)"
=======
    date = "2021-04-25T11:35:01+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_282 (Amazon.com Inc.)"
>>>>>>> Stashed changes
)
@Component
public class DocumentMapperImpl implements DocumentMapper {

    @Override
    public DocumentDto entityToDto(Document document) {
        if ( document == null ) {
            return null;
        }

        DocumentDto documentDto = new DocumentDto();

        documentDto.setId( document.getId() );
        documentDto.setName( document.getName() );
        documentDto.setCompany_id( document.getCompany_id() );
        documentDto.setUser_id( document.getUser_id() );
        documentDto.setRole_modify( document.getRole_modify() );
        documentDto.setRole_read( document.getRole_read() );

        return documentDto;
    }

    @Override
    public Document dtoToEntity(DocumentDto documentDto) {
        if ( documentDto == null ) {
            return null;
        }

        Document document = new Document();

        document.setId( documentDto.getId() );
        document.setName( documentDto.getName() );
        document.setCompany_id( documentDto.getCompany_id() );
        document.setUser_id( documentDto.getUser_id() );
        document.setRole_modify( documentDto.getRole_modify() );
        document.setRole_read( documentDto.getRole_read() );

        return document;
    }
}
