package com.wind.auth.utils;

import java.util.Random;

/**
 * SaltUtil
 *
 * @author qianchun
 * @date 2019/4/16
 **/
public class SaltUtil {
    private static final char[] SALT_ELE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /**
     * 生成salt
     *
     * @return
     */
    public static String generateSalt() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(SALT_ELE[random.nextInt(SALT_ELE.length)]);
        }
        return sb.toString();
    }
}
