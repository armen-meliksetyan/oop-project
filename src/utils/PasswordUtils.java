package utils;

import java.util.Base64;

public final class PasswordUtils {

    public static String hash(String password) {
        if (password == null) {
            return "";
        }

        String salted = "SALT123" + password + "SALT456";

        int hash = 0;
        for (char c : salted.toCharArray()) {
            hash = 31 * hash + c;
        }

        return Base64.getEncoder()
                .encodeToString(String.valueOf(hash).getBytes())
                .replace("=", "");
    }

    public static boolean verify(String input, String storedHash) {
        return storedHash.equals(hash(input));
    }
}
