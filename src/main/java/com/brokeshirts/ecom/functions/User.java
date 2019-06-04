package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.Users;
import com.brokeshirts.ecom.models.data.UsersDao;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class User {
    private static final SecureRandom RAND = new SecureRandom();
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

    private static Optional<String> generateSalt (final int length) {

        if (length < 1) {
            System.err.println("error in generateSalt: length must be > 0");
            return Optional.empty();
        }

        byte[] salt = new byte[length];
        RAND.nextBytes(salt);

        return Optional.of(Base64.getEncoder().encodeToString(salt));
    }

    private static Optional<String> hashPassword (String password, String salt) {

        char[] chars = password.toCharArray();
        byte[] bytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

        Arrays.fill(chars, Character.MIN_VALUE);

        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Optional.of(Base64.getEncoder().encodeToString(securePassword));

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.err.println("Exception encountered in hashPassword()");
            return Optional.empty();

        }finally {
            spec.clearPassword();
        }
    }

    private static boolean verifyPassword (String password, String key, String salt) {
        Optional<String> optEncrypted = hashPassword(password, salt);
        if (!optEncrypted.isPresent()) return false;
        return optEncrypted.get().equals(key);
    }

    public static void addUser (String email, String password, String first_name, String middle_initial, String last_name, UsersDao usersDao) {
        String salt = generateSalt(password.length()).orElse(null);
        String hash = hashPassword(password, salt).orElse(null);

        if(verifyPassword(password, hash, salt)) {
            Users newUser = new Users();
//            newUser.setAdmin(0);
            newUser.setEmail(email);
//            newUser.setHash(hash);
//            newUser.setSalt(salt);
            newUser.setFirst_name(first_name);
            newUser.setMiddle_initial(middle_initial);
            newUser.setLast_name(last_name);

            usersDao.save(newUser);
        }
    }

    public boolean logIn (String email, String password, UsersDao usersdao) {
        Users user = usersdao.findByEmail(email);
        boolean logStatus = false;

//        if(verifyPassword(password, user.getHash(), user.getSalt())) {
//            System.out.println("User " + email + " is logged in.");
//            logStatus = true;
//        } else {
//            System.out.println("Wrong Password!");
//        }

        return logStatus;
    }

}
