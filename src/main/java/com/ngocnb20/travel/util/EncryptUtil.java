package com.ngocnb20.travel.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public final class EncryptUtil {
    private EncryptUtil() {}
    public static String hashText(String plainText) {
        Objects.requireNonNull(plainText);
        StringBuilder hashText = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(plainText.getBytes());
            BigInteger no = new BigInteger(1,messageDigest);
            hashText = new StringBuilder(no.toString(16));
            
            while(hashText.length()<32) {
                hashText.insert(0, "0");
            }
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        assert hashText != null;
        return hashText.toString();
    }

    public static void main(String[] args) {
        System.out.println(hashText("abcd1234"));
    }
    
}
