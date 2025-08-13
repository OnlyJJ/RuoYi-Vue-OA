package com.ruoyi.tools.utils.compare;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author wocurr.com
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Compare {
    /**
     * 字段名称
     */
    String value() default "";

    /**
     * 格式化
     * @return
     */
    String format() default "";

    /**
     * 字段转义
     * @return
     */
    String fieldTranslate() default "string";
}