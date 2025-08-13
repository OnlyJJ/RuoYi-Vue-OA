package com.ruoyi.biz.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 业务表单处理异常 </p>
 *
 * @Author wocurr.com
 */
public class BizFormException extends BaseException {

    public BizFormException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public BizFormException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public BizFormException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public BizFormException(String code, Object[] args) {
        super(code, args);
    }

    public BizFormException(String defaultMessage) {
        super(defaultMessage);
    }
}
