package com.atc.utils;

import org.apache.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AuthUtils {
    private final static Logger LOG = Logger.getLogger(AuthUtils.class);
    private final static String HASH_AND_SALT_SEPARATOR = "++";
    // it's unsecure to use a fixed salt but it's easier for PINTEG
    private final static byte[] SALT = "A".getBytes(StandardCharsets.UTF_8);


    public final static String hashAndSalt(String password) {
        // source: https://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java

        LOG.info("Hashing and salting password...");
/*        byte[] SALT = new byte[16];
        Random random = new Random();
        random.nextBytes(SALT);*/
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT, 65536, 128);
        SecretKeyFactory f = null;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = new byte[0];
        try {
            hash = f.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        Base64.Encoder enc = Base64.getEncoder();

        LOG.info("salt: " + enc.encodeToString(SALT));
        LOG.info("hash: " + enc.encodeToString(hash));
        return enc.encodeToString(hash) + HASH_AND_SALT_SEPARATOR + enc.encodeToString(SALT);
    }
}
