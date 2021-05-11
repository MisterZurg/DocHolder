package com.docholder.service;

import com.docholder.model.*;
import com.docholder.repository.CompanyRepository;
import com.docholder.repository.DocumentRepository;
import com.docholder.repository.DocumentRequestRepository;
import com.docholder.repository.FtpRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{

    private final FtpRepository ftpRepository;
    private final DocumentRepository documentRepository;
    private final DocumentRequestRepository documentRequestRepository;
    private final CompanyRepository companyRepository;

    @Override
    public boolean putDocument(Document document, MultipartFile file) {
        if(file.getOriginalFilename() == null) return false;

        try {
            document.setFilename(generateFilename(file.getOriginalFilename()));
            boolean is_uploaded = ftpRepository.uploadToFtpServer(document.getFilename(), file);
            if (!is_uploaded) return false;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("catch error during putDocument() in FTP section");
            return false;
        }

        try {
            documentRepository.save(document);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("catch error during putDocument() in DB section");
            return false;
        }

        return true;
    }

    @Override
    public byte[] getDocument(UUID id) {
        byte[] bytes = new byte[0];
        Document document;

        try {
            document = documentRepository.getOne(id);
        } catch (Exception e){
            System.out.println("catch error during getDocument() in DB section");
            return bytes;
        }

        try {
            bytes = ftpRepository.downloadFromFtpServer(document.getFilename());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch error during getDocument() in FTP section");
        }

        return bytes;
    }

    @Override
    public boolean deleteDocument(UUID id){
        Document document = documentRepository.getOne(id);
        String filename = document.getFilename();

        try {
            documentRepository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("catch error during deleteDocument() in DB section");
            return false;
        }

        try {
            boolean is_deleted = ftpRepository.deleteFromFtpServer(filename);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("catch error during deleteDocument() in FTP section but we return true");
            return true;
        }

        return true;
    }

    @Override
    public Document getOneDocumentInfo(UUID id){
        try{
            return documentRepository.getOne(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Document> getDocumentsByCompany(UUID company_id){
        try {
            return documentRepository.findDocumentsByCompany(company_id.toString());
        } catch (Exception e){
            System.out.println("catch error during getDocumentsByCompany()");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean request(DocumentRequest documentRequest){

        try {
//            check for unique
            DocumentRequest requestByAllInfo = documentRequestRepository.findByAllInfo(
                    documentRequest.getUserId(),
                    documentRequest.getCompanyId(),
                    documentRequest.getDocumentId()
            );

            if (requestByAllInfo != null){
                requestByAllInfo.setMessage(documentRequest.getMessage());
                requestByAllInfo.setStatus(NoticeStatus.NOT_ANSWERED);
                documentRequestRepository.save(requestByAllInfo);
            }else{
                documentRequestRepository.save(documentRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    @Override
    public List<DocumentRequest> getRequestsByCompany(UUID companyId){
        return documentRequestRepository.findAllByCompany_id(companyId);
    }
    @Override
    public List<DocumentRequest> getRequestsByUser(UUID userId){
        return documentRequestRepository.findAllByUser_id(userId);
    }
    @Override
    public DocumentRequest getRequestByUserAndDocument(UUID userId, UUID documentId){
        return documentRequestRepository.getOneByUser_idAndDocument_id(userId, documentId);
    }
    @Override
    public boolean setRequestStatus(UUID id, NoticeStatus status){
        try{
            DocumentRequest request = documentRequestRepository.getOne(id);
            request.setStatus(status);
            documentRequestRepository.save(request);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }




    private String generateFilename(String oldFilename){
        UUID id = UUID.randomUUID();
        String extension = oldFilename.substring(oldFilename.lastIndexOf(".") + 1);

        return id+"."+extension;
    }
}
