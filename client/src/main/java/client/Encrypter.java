package client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {
    public static String encrypt(String input) {
        MessageDigest sha384 = null;
        try {
            sha384 = MessageDigest.getInstance("SHA-384"); //SHA-384
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = sha384.digest(input.getBytes());

        StringBuilder result = new StringBuilder();
        for (byte b : hash) {
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }
}

