package com.ruoyi.kbs.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * <p> 知识库处理异常 </p>
 *
 * @Author wocurr.com
 */
public class KbsHandleException extends BaseException {

    public KbsHandleException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public KbsHandleException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public KbsHandleException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public KbsHandleException(String code, Object[] args) {
        super(code, args);
    }

    public KbsHandleException(String defaultMessage) {
        super(defaultMessage);
    }
}
