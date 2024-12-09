package com.booking.encoder;

import com.booking.contract.EncodingInterface;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha512Encoder implements EncodingInterface, PasswordEncoder {

    public static final String SALT = "Some Random Salt";

    @Override
    public String encodeString(String StringToHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(SALT.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(StringToHash.getBytes(StandardCharsets.UTF_8));

            return new String(Hex.encode(bytes));
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("no SHA-512 algorithm encoder");
        }
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return encodeString(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        throw new RuntimeException("no upgrade implemented");
    }
}
