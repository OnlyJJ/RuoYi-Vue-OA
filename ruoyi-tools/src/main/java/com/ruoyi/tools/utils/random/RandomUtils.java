package com.ruoyi.tools.utils.random;

import java.security.SecureRandom;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;

/**
 * 随机数生成类
 * @author wocurr.com
 */
public class RandomUtils {

    private static final ThreadLocal<SplittableRandom> LOCAL_RANDOM = ThreadLocal.withInitial(SplittableRandom::new);

    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String PWD_CHARS = "123456789@abcdefghjkmnpqrstuvwxy#ABCDEFGHJKMNPQRSTUVWXY";

    private RandomUtils() {}

    /**
     * 获取一个介于 min 和 max 之间的随机整数（包括 min 和 max）。
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机整数
     */
    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * 获取一个介于 min 和 max 之间的随机长整型（包括 min 和 max）。
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机长整型
     */
    public static long randomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    /**
     * 获取一个介于 0.0 和 1.0 之间的随机浮点数。
     *
     * @return 随机浮点数
     */
    public static double randomDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    /**
     * 获取一个介于 min 和 max 之间的随机双精度数。
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机双精度数
     */
    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    /**
     * 获取一个介于 0.0 和 1.0 之间的随机浮点数。
     *
     * @return 随机浮点数
     */
    public static float randomFloat() {
        return ThreadLocalRandom.current().nextFloat();
    }

    /**
     * 获取一个介于 min 和 max 之间的随机浮点数。
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机浮点数
     */
    public static float randomFloat(float min, float max) {
        if (min >= max) {
            throw new IllegalArgumentException("参数错误");
        }
        return min + (max - min) * ThreadLocalRandom.current().nextFloat();
    }

    /**
     * 从指定数组中随机选取一个元素。
     *
     * @param array 数组
     * @param <T>   元素类型
     * @return 随机选取的元素
     */
    public static <T> T randomElement(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("参数错误");
        }
        return array[ThreadLocalRandom.current().nextInt(array.length)];
    }

    /**
     * 从指定集合中随机选取一个元素。
     *
     * @param collection 集合
     * @param <T>        元素类型
     * @return 随机选取的元素
     */
    public static <T> T randomElement(List<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("参数错误");
        }
        int index = ThreadLocalRandom.current().nextInt(collection.size());
        return collection.get(index);
    }

    /**
     * 获取一个随机布尔值。
     *
     * @return 随机布尔值
     */
    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * 使用 SplittableRandom 生成随机数。
     *
     * @return SplittableRandom 实例
     */
    public static SplittableRandom splittableRandom() {
        return LOCAL_RANDOM.get();
    }

    /**
     * 使用 SecureRandom 生成随机数。
     *
     * @return SecureRandom 实例
     */
    public static SecureRandom secureRandom() {
        return new SecureRandom();
    }

    /**
     * 生成一个固定长度的整数。
     *
     * @param length 长度
     * @return 固定长度的整数
     */
    public static int randomFixedInt(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("参数错误");
        }
        int minValue = (int) Math.pow(10, length - 1);
        int maxValue = (int) Math.pow(10, length) - 1;
        return ThreadLocalRandom.current().nextInt(minValue, maxValue + 1);
    }

    /**
     * 生成一个固定长度的字符串。
     *
     * @param length 长度
     * @return 固定长度的字符串
     */
    public static String randomFixedStr(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("参数错误");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current().nextInt(CHARS.length());
            sb.append(CHARS.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 生成一个固定长度的初始密码字符串（初始化密码使用，过滤了一些肉眼无法区分的字符）
     * @param length 长度
     * @return 固定长度的字符串
     */
    public static String randomInitPwd(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("参数错误");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current().nextInt(PWD_CHARS.length());
            sb.append(PWD_CHARS.charAt(index));
        }
        return sb.toString();
    }

}