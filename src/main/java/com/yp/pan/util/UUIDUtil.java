package com.yp.pan.util;

import java.util.Random;

/**
 *
 * @author Administrator
 * @date 2016/12/29
 */
public class UUIDUtil {

    private final static char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    private final static char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9'};

    /**
     * 随机ID生成器，由数字、小写字母和大写字母组成
     *
     * @return
     */
    public static String getUUID() {
        Random random = new Random();
        char[] cs = new char[32];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = DIGITS[random.nextInt(DIGITS.length)];
        }
        return new String(cs);
    }

    public static String getUUID(int size) {
        Random random = new Random();
        char[] cs = new char[size];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = DIGITS[random.nextInt(DIGITS.length)];
        }
        return new String(cs);
    }

    public static String getNumbers() {
        Random random = new Random();
        char[] cs = new char[6];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = NUMBERS[random.nextInt(NUMBERS.length)];
        }
        return new String(cs);
    }

}
