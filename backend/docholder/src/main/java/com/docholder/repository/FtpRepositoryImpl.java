package com.docholder.repository;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Repository
public class FtpRepositoryImpl implements FtpRepository{

    //    SHOULD I TRANSFER THIS PROPERTIES TO INTERFACE???????????????
    private final String username = "someuser";
    private final String password = "Qwerty123!";
    private final String url = "localhost";
    private final int port = 21;

    private FTPClient con = null;

    private void connect() throws Exception{
        con = new FTPClient();
        con.connect(url, port);
        con.login(username, password);
        con.enterLocalPassiveMode(); // important!
        con.setFileType(FTP.BINARY_FILE_TYPE);
    }
    private void disconnect() throws Exception{
        con.logout();
        con.disconnect();
    }

    @Override
    public boolean uploadToFtpServer(String filename, MultipartFile file) throws Exception{
        connect();

        boolean is_uploaded = con.storeFile(filename, file.getInputStream());

        disconnect();
        return is_uploaded;
    }

    @Override
    public byte[] downloadFromFtpServer(String filename) throws Exception{
        connect();

        InputStream remoteInput=con.retrieveFileStream(filename);
        byte[] fileBytes = StreamUtils.copyToByteArray(remoteInput);
        remoteInput.close();

        disconnect();
        return fileBytes;
    }

    @Override
    public boolean deleteFromFtpServer(String filename) throws Exception{
        connect();

        boolean is_deleted = con.deleteFile(filename);
        System.out.println("is_deleted="+is_deleted);

        disconnect();

        return true;
    }

}