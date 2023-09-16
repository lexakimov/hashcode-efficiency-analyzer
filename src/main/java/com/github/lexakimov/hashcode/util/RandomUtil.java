package com.github.lexakimov.hashcode.util;

import java.util.Random;

public class RandomUtil {

    public static String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        var random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    public static Integer generateRandomNumber() {
        var random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }
}
