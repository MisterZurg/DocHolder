package com.docholder.service;

import com.docholder.model.Document;
import com.docholder.repository.DocumentRepository;
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



    private String generateFilename(String oldFilename){
        UUID id = UUID.randomUUID();
        String extension = oldFilename.substring(oldFilename.lastIndexOf(".") + 1);

        return id+"."+extension;
    }
}
