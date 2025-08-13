package com.ruoyi.tools.utils.code;

import java.util.Arrays;

/**
 * 邀请码生成工具
 * @Author wocurr.com
 */
public class InvitateCodeGenerator {

    /**
     * 随机字符串
     */
    private static final char[] CHARS = new char[] {'f', 'l', 'g', 'w', '5', 'x', 'c', '3',
            '9', 'z', 'm', '6', '7', 'y', 'r', 't', '2', 'h', 's', '8', 'd', 'v', 'e', 'j', '4', 'k',
            'q', 'p', 'u', 'a', 'n', 'b'};

    private final static int CHARS_LENGTH = 32;
    /**
     * 邀请码长度
     */
    private final static int CODE_LENGTH = 6;

    /**
     * 随机数据
     */
    private final static long SLAT = 1234561L;

    /**
     * PRIME1 与 CHARS 的长度 L互质，可保证 ( id * PRIME1) % L 在 [0,L)上均匀分布
     */
    private final static int PRIME1 = 3;

    /**
     * PRIME2 与 CODE_LENGTH 互质，可保证 ( index * PRIME2) % CODE_LENGTH  在 [0，CODE_LENGTH）上均匀分布
     */
    private final static int PRIME2 = 11;

    private final static long DEFAULT_ID = 20201212L;


    /**
     * 生成邀请码，默认6位长度
     * @return
     */
    public static String gen() {
        return gen(DEFAULT_ID, CODE_LENGTH);
    }

    /**
     * 生成邀请码, 自定义长度
     * @param length 返回的邀请码长度
     * @return
     */
    public static String gen(int length) {
        return gen(DEFAULT_ID, length);
    }

    /**
     * 生成邀请码
     *
     * @param id 唯一的id主键. 支持的最大值为: (32^7 - {@link #SLAT})/{@link #PRIME1}
     * @return code
     */
    private static String gen(Long id, int length) {
        // 补位
        id = id * PRIME1 + SLAT;
        //将 id 转换成32进制的值
        long[] b = new long[length];
        // 32进制数
        b[0] = id;
        for (int i = 0; i < length - 1; i++) {
            b[i + 1] = b[i] / CHARS_LENGTH;
            // 按位扩散
            b[i] = (b[i] + i * b[0]) % CHARS_LENGTH;
        }

        long tmp = 0;
        for (int i = 0; i < length - 2; i++) {
            tmp += b[i];
        }
        b[length - 1] = tmp * PRIME1 % CHARS_LENGTH;

        // 进行混淆
        long[] codeIndexArray = new long[length];
        for (int i = 0; i < length; i++) {
            codeIndexArray[i] = b[i * PRIME2 % length];
        }

        StringBuilder buffer = new StringBuilder();
        Arrays.stream(codeIndexArray).boxed().map(Long::intValue).map(t -> CHARS[t]).forEach(buffer::append);
        return buffer.toString();
    }

}
