package com.weiwensangsang.service.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;

public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Generates a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generates an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generates a reset key.
     *
     * @return the generated reset key
     */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    // bsb v2 migrate from v1
    public static String get4SMSCode() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(9) + 1);
        }
        return sb.toString();
    }


    public static String get4InviteCode() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(9) + 1);
        }
        return sb.toString();
    }

    public static String get11Phone() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return "TES" + sb.toString();
    }

    public static String getScore() {
        int persent = (int) (Math.random() * 100) % 10;
        if (persent <= 3) {
            Random random = new Random();
            StringBuffer sb = new StringBuffer();
            sb.append(random.nextInt(5));
            sb.append(":");
            sb.append(random.nextInt(5));
            return sb.toString();
        } else if (persent <= 8) {
            return "1:0";
        } else {
            return "waitSubmit";
        }
    }

    public static String getRandomString() {
        String[] name = {"北邮吴彦祖", "北邮马德华", "北大锤霸天"};
        String[] action = {"鸡年大吉吧", "心明眼亮", "腰酸腿疼", "不写代码"};
        int persentName = (int) (Math.random() * 100) % 5;
        int persentAction = (int) (Math.random() * 100) % 5;
        try{
            return new StringBuilder().append(name[persentName]).append("祝您").append(action[persentAction]).toString();
        } catch (Exception e){
            return "北大锤霸天祝您腰酸腿疼";
        }
    }

    public static Long getRandomLong() {
        return Long.parseLong(get4SMSCode());
    }

    public static Long getLength() {
        Random random = new Random();
        Long[] longs = {1L, 3L, 4L, 5L, 7L, 8L};
        return longs[random.nextInt(5)];
    }

    public static Long getBike() {
        Random random = new Random();
        return Integer.toUnsignedLong(random.nextInt(4));
    }

    public static Long getBikeOil() {
        Random random = new Random();
        return Integer.toUnsignedLong(random.nextInt(99) + 1);
    }

    public static int getLocate(int limit) {
        Random random = new Random();
        return random.nextInt(limit);
    }

    public static String getCity() {
        Random random = new Random();
        String[] s = {"北京", "上海", "杭州", "南昌", "广州","海口"};
        return s[random.nextInt(6)];
    }

}
