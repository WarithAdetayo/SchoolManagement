package org.example.sms.service;

import org.example.sms.entity.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Date;

public class UserService {

    public static void newUser(User user, String password, User creator) {
        user.setDateCreated(new Date());
        setPassword(user, password);
        user.setCreatedBy(creator);
        user.setUserType(user.getClass().getName());
    }

    public static void modifyUser(User user, User modifier) {
        user.setDateModified(new Date());
        user.setLastModifiedBy(modifier);
    }

    public static boolean checkPassword(User user, String password) {

        if (user.getPasswordHash() == null || user.getPasswordSalt() == null)
            return false;

        try {
            String passwordHash = getPasswordHash(password, user.getPasswordSalt().getBytes());
            return passwordHash.equals(user.getPasswordHash());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPassword(User user, String password) {
        try {
            byte[] salt = generateSalt();
            user.setPasswordSalt(byteToString(salt));
            user.setPasswordHash(getPasswordHash(password, user.getPasswordSalt().getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPasswordHash(String clearPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeySpec spec = new PBEKeySpec(clearPassword.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return byteToString(hash);
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private static String byteToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
