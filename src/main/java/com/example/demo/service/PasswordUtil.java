package com.example.demo.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    public static String[] hashPassword(String password) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);
        byte[] hashedPassword = digest.digest(password.getBytes());

        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hashedPassword);

        return new String[] { saltBase64, hashBase64 };
    }

    public static boolean verifyPassword(String password, String storedSalt, String storedHash) throws NoSuchAlgorithmException {
        byte[] salt = Base64.getDecoder().decode(storedSalt);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);
        byte[] hashedPassword = digest.digest(password.getBytes());

        String hashBase64 = Base64.getEncoder().encodeToString(hashedPassword);

        return hashBase64.equals(storedHash);
    }

}
