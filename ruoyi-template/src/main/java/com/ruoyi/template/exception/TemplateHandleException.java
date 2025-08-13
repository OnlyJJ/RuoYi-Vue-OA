package com.ruoyi.template.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 模板处理异常 </p>
 *
 * @Author wocurr.com
 */
public class TemplateHandleException extends BaseException {

    public TemplateHandleException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public TemplateHandleException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public TemplateHandleException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public TemplateHandleException(String code, Object[] args) {
        super(code, args);
    }

    public TemplateHandleException(String defaultMessage) {
        super(defaultMessage);
    }
}
