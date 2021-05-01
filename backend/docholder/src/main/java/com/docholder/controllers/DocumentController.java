package com.docholder.controllers;

import com.docholder.model.CompanyMapper;
import com.docholder.model.Document;
import com.docholder.model.DocumentDto;
import com.docholder.model.DocumentMapper;
import com.docholder.repository.FtpRepository;
import com.docholder.service.CompanyService;
import com.docholder.service.DocumentService;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    //    public ResponseEntity<?> putDocument(@RequestParam UUID id, @RequestPart("file") MultipartFile file){
    @PreAuthorize("hasPermission(new com.docholder.utilities.DocumentSecurityTransfer(#documentDto, #token, null), 'putDocument')")
    @PostMapping(value = "/upload", consumes = {
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> putDocument(
            @RequestPart String token,
            @ApiParam(hidden = false) @RequestPart("documentDto") DocumentDto documentDto,
            @RequestPart("file") MultipartFile file
    ){
        Document document = documentMapper.dtoToEntity(documentDto);
        boolean is_uploaded = documentService.putDocument(document, file);
        return is_uploaded
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasPermission(new com.docholder.utilities.DocumentSecurityTransfer(null, #token, #id), 'readDocument')")
    @PostMapping(value = "/download")
    public ResponseEntity<?> getDocument(@RequestParam UUID id, @RequestParam String token){
        byte[] bytes = documentService.getDocument(id);

        return bytes.length != 0
                ? new ResponseEntity<>(bytes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/byCompany")
    public ResponseEntity<?> getDocumentsByCompany(@RequestParam(name = "id") UUID company_id){
        List<Document> documents = documentService.getDocumentsByCompany(company_id);
        List<DocumentDto> documentsDto = documentMapper.entityToDto(documents);
        return documents != null && !documents.isEmpty()
                ? new ResponseEntity<>(documents, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
