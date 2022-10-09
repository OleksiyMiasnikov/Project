package com.myProject.util;

import com.myProject.service.exception.DaoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword {
    public static String encrypt(String password) throws DaoException {
        // MessageDigest instance for MD5
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new DaoException(e);
        }

        // Add plain-text password bytes to digest using MD5 update() method
        md5.update(password.getBytes());

        // Convert the hash value into bytes
        byte[] bytes = md5.digest();

        // The bytes array has bytes in decimal form.
        // Converting it into hexadecimal format.
        StringBuilder encryptedPassword = new StringBuilder();
        for (byte aByte : bytes) {
            encryptedPassword
                    .append(Integer.toString((aByte & 0xff) + 0x100, 16)
                            .substring(1));
        }

        // Complete hashed password in hexadecimal format
        return encryptedPassword.toString();
    }

}
