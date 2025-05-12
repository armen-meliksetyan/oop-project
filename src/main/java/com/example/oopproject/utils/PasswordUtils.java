package com.example.oopproject.utils;

import java.util.Base64;

/**
 * Utility class for handling password hashing and verification.
 * <p>
 * This class provides methods to hash a password using a simple salted hashing algorithm
 * and verify if an input password matches a stored hash.
 * </p>
 */
public final class PasswordUtils {

    /**
     * Hashes a given password using a salted algorithm.
     * <p>
     * The password is salted with predefined strings, and a simple hash is computed.
     * The resulting hash is then Base64 encoded to generate a secure string representation.
     * </p>
     *
     * @param password the password to be hashed
     * @return the Base64-encoded hashed password as a string
     */
    public static String hash(String password) {
        if (password == null) {
            return "";
        }

        // Salt the password to improve security
        String salted = "SALT123" + password + "SALT456";

        // Simple hash calculation
        int hash = 0;
        for (char c : salted.toCharArray()) {
            hash = 31 * hash + c;
        }

        // Base64 encode the hash and return it
        return Base64.getEncoder()
                .encodeToString(String.valueOf(hash).getBytes())
                .replace("=", "");
    }

    /**
     * Verifies if the input password matches the stored hash.
     * <p>
     * This method hashes the input password and compares it with the stored hash.
     * If they match, the method returns true; otherwise, it returns false.
     * </p>
     *
     * @param input the password to be verified
     * @param storedHash the stored hash to compare with
     * @return true if the input matches the stored hash, false otherwise
     */
    public static boolean verify(String input, String storedHash) {
        return storedHash.equals(hash(input));
    }
}
