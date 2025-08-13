package com.ruoyi.tools.utils.compare;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author wocurr.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CompareNode {
    /**
     * 字段
     */
    private String fieldKey;

    /**
     * 字段值
     */
    private Object fieldValue;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段转义
     */
    private String fieldTranslate;

    /**
     * 格式化
     */
    private String format;

}
