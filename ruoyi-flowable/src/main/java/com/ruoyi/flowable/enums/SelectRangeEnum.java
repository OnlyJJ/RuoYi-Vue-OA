package com.ruoyi.flowable.enums;

/**
 * <p> 选人范围枚举 </p>
 *
 * @Author wocurr.com
 */
public enum SelectRangeEnum {
    CORP("corp", "公司"),
    DEPT("dept", "部门"),
    ;

    private final String code;
    private final String message;

    SelectRangeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
