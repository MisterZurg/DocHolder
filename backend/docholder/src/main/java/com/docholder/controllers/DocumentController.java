package com.docholder.controllers;

import com.docholder.model.*;
import com.docholder.repository.CompanyRepository;
import com.docholder.repository.DocumentRepository;
import com.docholder.repository.FtpRepository;
import com.docholder.repository.UserRepository;
import com.docholder.service.CompanyService;
import com.docholder.service.DocumentService;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
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
    private final DocumentRequestMapper documentRequestMapper;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

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
    @GetMapping(value = "/download")
    public ResponseEntity<?> getDocument(@RequestParam UUID id, @RequestParam String token){
        byte[] bytes = documentService.getDocument(id);

        return bytes.length != 0
                ? new ResponseEntity<>(bytes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasPermission(new com.docholder.utilities.DocumentSecurityTransfer(null, #token, #id), 'deleteDocument')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteDocument(@RequestParam UUID id, @RequestParam String token){
        boolean is_deleted = documentService.deleteDocument(id);

        return is_deleted
                ? new ResponseEntity<>(HttpStatus.OK)
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

    @PreAuthorize("hasPermission(#token, 'tokenValidate')")
    @PostMapping(value = "/request")
    public ResponseEntity<?> request(@RequestBody DocumentRequest documentRequest, @RequestParam String token){

        return documentService.request(documentRequest)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @GetMapping(value = "/requestByCompany")
    public ResponseEntity<?> getRequestsByCompany(@RequestParam(name = "company_id") UUID companyId){
        List<DocumentRequest> documentRequests = documentService.getRequestsByCompany(companyId);
        List<DocumentRequestDto> documentRequestsDto = documentRequestMapper.entityToDto(documentRequests);
        documentRequestsDto.stream().forEach(documentRequestDto -> {
            documentRequestDto.setCompany_name( companyRepository.getOne(documentRequestDto.getCompany_id()).getName() );
            documentRequestDto.setUser_name( userRepository.getOne(documentRequestDto.getUser_id()).getName() );
            documentRequestDto.setDocument_name( documentRepository.getOne(documentRequestDto.getDocument_id()).getName() );
        });
        return new ResponseEntity<>(documentRequestsDto, HttpStatus.OK);
    }                   ////// this we can unite to one method
    @GetMapping(value = "/requestByUser")
    public ResponseEntity<?> getRequestsByUser(@RequestParam(name = "user_id") UUID userId){
        List<DocumentRequest> documentRequests = documentService.getRequestsByUser(userId);
        List<DocumentRequestDto> documentRequestsDto = documentRequestMapper.entityToDto(documentRequests);
        documentRequestsDto.stream().forEach(documentRequestDto -> {
            documentRequestDto.setCompany_name( companyRepository.getOne(documentRequestDto.getCompany_id()).getName() );
            documentRequestDto.setUser_name( userRepository.getOne(documentRequestDto.getUser_id()).getName() );
            documentRequestDto.setDocument_name( documentRepository.getOne(documentRequestDto.getDocument_id()).getName() );
        });
        return new ResponseEntity<>(documentRequestsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(new com.docholder.utilities.DocumentSecurityTransfer(null, #token, #id), 'setStatusDocumentRequest')")
    @PostMapping(value = "/request/status")
    public ResponseEntity<?> setRequestStatus(
            @RequestParam UUID id,
            @RequestParam NoticeStatus status,
            @RequestParam String token)
    {
        return documentService.setRequestStatus(id, status)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
