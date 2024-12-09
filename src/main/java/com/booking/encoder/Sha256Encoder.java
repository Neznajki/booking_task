package com.booking.encoder;

import com.booking.contract.EncodingInterface;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Encoder implements EncodingInterface {

    public static final String SALT = "Some Random Salt";

    @Override
    public String encodeString(String StringToHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(SALT.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(StringToHash.getBytes(StandardCharsets.UTF_8));

            return new String(Hex.encode(bytes));
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("no SHA-512 algorithm encoder");
        }
    }
}
