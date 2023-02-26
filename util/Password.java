package util;

import java.security.MessageDigest;

public class Password {
    public static String encode(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update("".getBytes());
            StringBuilder res = new StringBuilder("");
            for (byte b : digest.digest(password.getBytes()))
                res.append(String.format("%02x", b));
            return res.toString();
        } catch (Exception e) { System.err.println(e.getLocalizedMessage()); }
        return null;
    }
    public static boolean compare(String str, String hash) {
        return Password.encode(str).equals(hash);
    }
}