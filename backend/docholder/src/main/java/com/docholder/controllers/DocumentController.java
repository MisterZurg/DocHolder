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
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.print.Doc;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
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

    @PreAuthorize("hasPermission(new com.docholder.utilities.DocumentSecurityTransfer(#documentDto, #token, null), 'putDocument')")
    @PostMapping(value = "/upload", consumes = {
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(
            @ApiParam(hidden = false) @RequestPart("documentDto") DocumentDto documentDto,
            @RequestPart("file") MultipartFile file,
            @RequestHeader(value="Authorization", required = false) String token
    ){
        Document document = documentMapper.dtoToEntity(documentDto);
        boolean is_uploaded = documentService.putDocument(document, file);
        return is_uploaded
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasPermission(new com.docholder.utilities.DocumentSecurityTransfer(null, #token, #id), 'readDocument')")
    @GetMapping(value = "/{id}/download")
    public ResponseEntity<?> download(
            @PathVariable UUID id,
            @RequestHeader(value="Authorization", required = false) String token
    ){
        byte[] bytes = documentService.getDocument(id);

        return bytes.length != 0
                ? new ResponseEntity<>(bytes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasPermission(new com.docholder.utilities.DocumentSecurityTransfer(null, #token, #id), 'deleteDocument')")
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            @RequestHeader(value="Authorization", required = false) String token
    ){
        boolean is_deleted = documentService.deleteDocument(id);

        return is_deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/company")
    public ResponseEntity<?> getDocumentsByCompany(@RequestParam(name = "company_id") UUID companyId){
        List<Document> documents = documentService.getDocumentsByCompany(companyId);
        List<DocumentDto> documentsDto = documentMapper.entityToDto(documents);
        return documents != null && !documents.isEmpty()
                ? new ResponseEntity<>(documents, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasPermission(#token, 'tokenValidate')")
    @PostMapping(value = "/request")
    public ResponseEntity<?> request(
            @RequestBody DocumentRequest documentRequest,
            @RequestHeader(value="Authorization", required = false) String token
    ){

        return documentService.request(documentRequest)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/requests")
    public ResponseEntity<?> getRequests(
            @RequestParam(name = "user_id") @Nullable UUID userId,
            @RequestParam(name = "company_id") @Nullable UUID companyId
    ){
        List<DocumentRequest> documentRequests;

        if (companyId == null)
            documentRequests = documentService.getRequestsByUser(userId);
        else if (userId == null)
            documentRequests = documentService.getRequestsByCompany(companyId);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<UUID> companyUUIDs = documentRequests.stream().map(DocumentRequest::getCompanyId).collect(Collectors.toList());
        Map<UUID, String> companyMap = companyRepository.findAllById(companyUUIDs).stream().collect(Collectors.toMap(Company::getId, Company::getName));

        List<UUID> userUUIDs = documentRequests.stream().map(DocumentRequest::getUserId).collect(Collectors.toList());
        Map<UUID, String> userMap = userRepository.findAllById(userUUIDs).stream().collect(Collectors.toMap(User::getId, user -> user.getName()+" "+user.getSurname()));

        List<UUID> documentUUIDs = documentRequests.stream().map(DocumentRequest::getDocumentId).collect(Collectors.toList());
        Map<UUID, String> documentMap = documentRepository.findAllById(documentUUIDs).stream().collect(Collectors.toMap(Document::getId, Document::getName));

        List<DocumentRequestDto> documentRequestsDto = documentRequestMapper.entityToDto(documentRequests);
        documentRequestsDto.forEach(documentRequestDto -> {
            documentRequestDto.setCompanyName( companyMap.get(documentRequestDto.getCompanyId()) );
            documentRequestDto.setUserFullName( userMap.get(documentRequestDto.getUserId()) );
            documentRequestDto.setDocumentName( documentMap.get(documentRequestDto.getDocumentId()) );
        });

        return new ResponseEntity<>(documentRequestsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(new com.docholder.utilities.DocumentSecurityTransfer(null, #token, #id), 'setStatusDocumentRequest')")
    @PutMapping(value = "/request/status")
    public ResponseEntity<?> setRequestStatus(
            @RequestParam UUID id,
            @RequestParam NoticeStatus status,
            @RequestHeader(value="Authorization", required = false) String token
    ){
        return documentService.setRequestStatus(id, status)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
