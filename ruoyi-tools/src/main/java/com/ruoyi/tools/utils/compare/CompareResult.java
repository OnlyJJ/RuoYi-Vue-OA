package com.ruoyi.tools.utils.compare;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author wocurr.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CompareResult {
    /**
     * 字段
     */
    private String fieldKey;

    /**
     * 字段值（当前值）
     */
    private Object fieldValue;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 原字段值
     */
    private Object origFieldValue;

    /**
     * 字段转义
     */
    private String fieldTranslate;
}
