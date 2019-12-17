package site.itxia.apiservice.util;

import java.util.Random;

/**
 * @author zhenxi
 */
public class TokenUtil {

    private TokenUtil() {
    }

    private static final int LENGTH = 4;
    private static final String CHARS = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final Random RANDOM = new Random();

    public static String generateToken() {
        return randomString();
    }

    private static String randomString() {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < LENGTH; ++i) {
            stringBuilder.append(randomChar());
        }
        return stringBuilder.toString();
    }

    private static char randomChar() {
        return CHARS.charAt(RANDOM.nextInt(CHARS.length()));
    }

}
