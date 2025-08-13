package com.ruoyi.tools.utils.code;

import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.tools.utils.date.LocalDateTimeUtil;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 用户账号生成器<br>
 * 说明：<br>
 * 1、本工具适用于并发低的场景，高并发请谨慎适用。
 *
 * @Author wocurr.com
 */
public class UserAccountGenerator {

    private static final String CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuuvwxyz";
    private static final String NUMBER_CHARACTER = "0123456789";

    private static final int DEFAULT_CHAR_LEN = 1;
    private static final int DEFAULT_NUMBER_LEN = 2;


    /**
     * 生成随机码
     * @return 1位随机字符 + 15位日期（年月日时分秒毫秒） + 2位随机数字
     */
    public static String gen() {
        StringBuilder result = new StringBuilder();
        String dateStr = LocalDateTimeUtil.formatNow(LocalDateTimeUtil.FORMAT_MDHMSS);
        result.append(genRandom(DEFAULT_CHAR_LEN, CHARACTER)).append(dateStr).append(genRandom(DEFAULT_NUMBER_LEN, NUMBER_CHARACTER));
        return result.toString();
    }

    /**
     * 生成随机码
     *
     * @return
     */
    private static String genRandom(int length, String character) {
        final Random rd = Holder.srd;
        Set<String> result = new HashSet<>();
        while (result.size() < length) {
            int index = (int) (rd.nextFloat() * character.length());
            String c = String.valueOf(character.charAt(index));
            result.add(c);
        }
        return String.join("", result);
    }

    /**
     * 强随机生成器单例
     */
    private static class Holder {
        static final SecureRandom srd = getSecureRandom();
    }

    private static SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new BaseException("初始化随机码失败！");
        }
    }
}
