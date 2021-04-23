package com.docholder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

public interface FtpRepository{
    boolean uploadToFtpServer(String filename, MultipartFile file) throws Exception;
    byte[] downloadFromFtpServer(String filename) throws Exception;
}
