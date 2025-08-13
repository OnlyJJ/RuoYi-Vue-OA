package com.ruoyi.tools.utils.code;

/**
 * 验证码生成工具
 *
 * @Author wocurr.com
 */
public class VerifyCodeGenerator {

    /**
     * 随机生成指定位数数字
     *
     * @param count
     * @return
     */
    public static String gen(int count) {
        return String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, count - 1)));
    }

}
