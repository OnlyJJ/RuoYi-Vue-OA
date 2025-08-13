package com.ruoyi.tools.utils.code;

import com.ruoyi.tools.utils.date.LocalDateTimeUtil;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 昵称生成工具<br>
 *   示例：1位字母 + 月日 + 2位随机数字
 *
 * @Author wocurr.com
 */
public class NickNameGenerator {

    private static final String CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHARACTER = "0123456789";

    private static final int DEFAULT_CHAR_LEN = 1;
    private static final int DEFAULT_NUMBER_LEN = 2;


    /**
     * 生成昵称
     * @return
     */
    public static String gen() {
        StringBuilder result = new StringBuilder();
        String dateStr = LocalDateTimeUtil.formatNow(LocalDateTimeUtil.FORMAT_MD);
        result.append(genRandom(DEFAULT_CHAR_LEN, CHARACTER)).append(dateStr)
                .append(genRandom(DEFAULT_NUMBER_LEN, NUMBER_CHARACTER));
        return result.toString();
    }

    /**
     * 生成随机码
     *
     * @return
     */
    private static String genRandom(int length, String charater) {
        final Random rd = getRandom();
        Set<String> result = new HashSet<>();
        while (result.size() < length) {
            int index = (int) (rd.nextFloat() * charater.length());
            String charat = String.valueOf(charater.charAt(index));
            result.add(charat);
        }
        return String.join("", result);
    }


    /**
     * 获取随机数生成器对象
     * 提供并发产生随机数，能够解决多个线程发生的竞争争夺。
     */
    private static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

}
