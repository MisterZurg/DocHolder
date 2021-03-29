package com.docholder.utilities;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// I don't write comments because
// I don't sure that this class is necessary

public class Encrypt {
    public String sha256(String password){
        String hex = null;

        try {
            byte[] data = password.getBytes("UTF-8");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest = messageDigest.digest(data);
            hex = String.format("%064x", new BigInteger(1, digest));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hex;
    }
}